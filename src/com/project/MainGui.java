package com.project;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class MainGui {
    private JFrame frame;
    private DefaultListModel<Post> postsModel = new DefaultListModel<>();
    private JList<Post> postsList = new JList<>(postsModel);
    private JTextArea logArea = new JTextArea(8, 30);

    private List<Post> posts = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    private JComboBox<User> userSelector;
    private DefaultListModel<String> commentsModel = new DefaultListModel<>();
    private JList<String> commentsList = new JList<>(commentsModel);

    public MainGui() {
        initData();
        initUI();
    }

    private void initData() {
        try {
            User kiki = new User("u1", "Kiki", URI.create("http://example.com/kiki.png").toURL());
            User nada = new User("u2", "Nada", URI.create("http://example.com/nada.png").toURL());
            User syahrul = new User("u3", "Syahrul", URI.create("http://example.com/syahrul.png").toURL());
            users.add(kiki); users.add(nada); users.add(syahrul);

            Account accKiki = new Account("kiki", "kiki@example.com", "pass1"); accKiki.setOwner(kiki); kiki.setAccount(accKiki);
            Account accNada = new Account("nada", "nada@example.com", "pass2"); accNada.setOwner(nada); nada.setAccount(accNada);

            PhotoPost p1 = new PhotoPost("p1", "Sunset at the beach", "http://example.com/photo1.jpg", "vivid");
            p1.addComment(new Comment("c1", "Great shot!"));
            p1.addLike(nada);

            VideoPost v1 = new VideoPost("v2", "Funny clip", "http://example.com/vid.mp4", 12);
            v1.addComment(new Comment("c2", "Haha nice"));

            posts.add(p1); posts.add(v1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initUI() {
        frame = new JFrame("Instagram-ish Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10,10));
        frame.getRootPane().setBorder(new EmptyBorder(10,10,10,10));

        // Top: user selector & actions
        JPanel topPanel = new JPanel(new BorderLayout(8,8));
        userSelector = new JComboBox<>(users.toArray(new User[0]));
        userSelector.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof User) setText(((User)value).getName());
                return this;
            }
        });
        topPanel.add(new JLabel("Active user:"), BorderLayout.WEST);
        topPanel.add(userSelector, BorderLayout.CENTER);

        frame.add(topPanel, BorderLayout.NORTH);

        // Center: posts list and detail
        JPanel center = new JPanel(new GridLayout(1,2,10,10));

        // posts list with custom renderer
        postsModel.clear();
        for (Post p : posts) postsModel.addElement(p);
        postsList.setCellRenderer(new PostRenderer());
        postsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        postsList.addListSelectionListener(e -> showPostDetail(postsList.getSelectedValue()));

        JScrollPane postsScroll = new JScrollPane(postsList);
        postsScroll.setBorder(BorderFactory.createTitledBorder("Posts"));

        // detail panel
        JPanel detail = new JPanel(new BorderLayout(6,6));
        detail.setBorder(BorderFactory.createTitledBorder("Post Detail"));

        commentsList.setBorder(new EmptyBorder(4,4,4,4));
        detail.add(new JScrollPane(commentsList), BorderLayout.CENTER);

        JPanel detailControls = new JPanel(new GridLayout(4,1,4,4));
        JButton likeBtn = new JButton("Like");
        JButton dmBtn = new JButton("Send DM");
        JTextField commentField = new JTextField();
        JButton addCommentBtn = new JButton("Add Comment");

        detailControls.add(likeBtn);
        detailControls.add(dmBtn);
        detailControls.add(commentField);
        detailControls.add(addCommentBtn);

        detail.add(detailControls, BorderLayout.EAST);

        center.add(postsScroll);
        center.add(detail);

        frame.add(center, BorderLayout.CENTER);

        // Right: activity log
        logArea.setEditable(false);
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setPreferredSize(new Dimension(260, 0));
        logScroll.setBorder(BorderFactory.createTitledBorder("Activity"));
        frame.add(logScroll, BorderLayout.EAST);

        // actions behavior
        likeBtn.addActionListener(ae -> {
            Post sel = postsList.getSelectedValue();
            User active = (User)userSelector.getSelectedItem();
            if (sel != null && active != null) {
                sel.addLike(active);
                log(active.getName() + " liked " + sel.getPostID());
                postsList.repaint();
                showPostDetail(sel);
            }
        });

        dmBtn.addActionListener(ae -> {
            User active = (User)userSelector.getSelectedItem();
            User target = users.size() > 1 ? users.get((userSelector.getSelectedIndex()+1) % users.size()) : null;
            if (active != null && target != null) {
                DirectMessage dm = new DirectMessage("dm"+System.currentTimeMillis(), "Hai dari " + active.getName(), active, target);
                dm.sendMessage();
                log("DM from " + active.getName() + " to " + target.getName());
            }
        });

        addCommentBtn.addActionListener(ae -> {
            Post sel = postsList.getSelectedValue();
            User active = (User)userSelector.getSelectedItem();
            String text = commentField.getText().trim();
            if (sel != null && active != null && !text.isEmpty()) {
                Comment nc = new Comment("c"+System.currentTimeMillis(), active.getName() + ": " + text);
                sel.addComment(nc);
                log(active.getName() + " commented on " + sel.getPostID());
                commentField.setText("");
                showPostDetail(sel);
            }
        });

        frame.setSize(900,600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void showPostDetail(Post post) {
        commentsModel.clear();
        if (post == null) return;
        for (Comment c : post.getComments()) commentsModel.addElement(c.getCommentID() + ": " + c.getText());
        // sync commentsList model
        commentsList.setModel(commentsModel);
    }

    private void log(String msg) { logArea.append(msg + "\n"); }

    private static class PostRenderer extends JPanel implements ListCellRenderer<Post> {
        private JLabel title = new JLabel();
        private JLabel meta = new JLabel();

        public PostRenderer() {
            setLayout(new BorderLayout(6,6));
            title.setFont(title.getFont().deriveFont(Font.BOLD, 14f));
            meta.setFont(meta.getFont().deriveFont(Font.PLAIN, 12f));
            add(title, BorderLayout.CENTER);
            add(meta, BorderLayout.EAST);
            setBorder(new EmptyBorder(6,6,6,6));
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Post> list, Post value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value == null) return this;
            String icon = value instanceof PhotoPost ? "📷" : value instanceof VideoPost ? "🎬" : "✳️";
            title.setText(icon + " " + value.getCaption());
            meta.setText("likes: " + value.getLikes().size());
            setBackground(isSelected ? new Color(220,240,255) : Color.WHITE);
            setOpaque(true);
            return this;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGui());
    }
}
