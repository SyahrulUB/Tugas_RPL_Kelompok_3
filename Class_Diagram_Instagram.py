import graphviz
# Install Python Versi terbaru dan Graphviz library
# Pastikan juga Graphviz software sudah terinstal di sistem Anda
# Untuk instalasi Graphviz library: pip install graphviz

# Inisialisasi objek Digraph (Directed Graph)
dot = graphviz.Digraph('class_diagram', comment='Instagram Class Diagram')
dot.attr('graph', rankdir='BT', splines='ortho') # Bottom to Top layout
dot.attr('node', shape='record', style='rounded', fontname='Helvetica')
dot.attr('edge', fontname='Helvetica', fontsize='10')

# --- Definisi Kelas dan Interface ---

# 1. Abstract Class & Interfaces
dot.node('Post',
         r'{Post (Abstract)\n|'
         r'- postID: String\l'
         r'- caption: String\l'
         r'- timestamp: DateTime\l|'
         r'+ addComment(comment: Comment)\l'
         r'+ addLike(user: User)\l'
         r'+ removeLike(user: User)\l}',
         fillcolor='lightgrey', style='filled,rounded')

dot.node('ModerationService', '''<
<TABLE BORDER="0" CELLBORDER="1" CELLSPACING="0" BGCOLOR="lightyellow">
  <TR><TD ALIGN="CENTER">&lt;&lt;Interface&gt;&gt;<BR/><B>ModerationService</B></TD></TR>
  <TR><TD ALIGN="LEFT" BALIGN="LEFT">
    + scanContent(content: String): boolean<BR/>
  </TD></TR>
</TABLE>>''', shape='plaintext', fillcolor='lightyellow', style='filled')

dot.node('NotificationService', '''<
<TABLE BORDER="0" CELLBORDER="1" CELLSPACING="0" BGCOLOR="lightyellow">
  <TR><TD ALIGN="CENTER">&lt;&lt;Interface&gt;&gt;<BR/><B>NotificationService</B></TD></TR>
  <TR><TD ALIGN="LEFT" BALIGN="LEFT">
    + sendPushNotification(user: User, message: String)<BR/>
    + sendWebSocketUpdate(user: User, data: JSON)<BR/>
  </TD></TR>
</TABLE>>''', shape='plaintext', fillcolor='lightyellow', style='filled')

# 2. Kelas Utama (Core Classes)
dot.node('User',
         r'{User|'
         r'- userID: String\l'
         r'- name: String\l'
         r'- profilePicture: URL\l|'
         r'+ createPost(content, caption)\l'
         r'+ createStory(content)\l'
         r'+ followUser(userToFollow: User)\l'
         r'+ sendMessage(recipient, message)\l}',
         fillcolor='lightblue', style='filled,rounded')

dot.node('Account',
         r'{Account|'
         r'- username: String\l'
         r'- email: String\l'
         r'- Password: String\l|'
         r'+ login(password): boolean\l'
         r'+ logout()\l'
         r'+ resetPassword()\l}',
         fillcolor='lightblue', style='filled,rounded')

# 3. Kelas Turunan Post (Concrete Post Classes)
dot.node('PhotoPost',
         r'{PhotoPost|'
         r'- imageURL: URL\l'
         r'- filter: String\l}',
         fillcolor='azure', style='filled,rounded')

dot.node('VideoPost',
         r'{VideoPost|'
         r'- videoURL: URL\l'
         r'- duration: int\l}',
         fillcolor='azure', style='filled,rounded')

# 4. Kelas Lainnya
dot.node('Story',
         r'{Story|'
         r'- storyID: String\l'
         r'- contentURL: URL\l'
         r'- timestamp: DateTime\l'
         r'- duration: int = 24h\l|'
         r'+ addView(viewer: User)\l}',
         fillcolor='lightcyan', style='filled,rounded')

dot.node('Comment',
         r'{Comment|'
         r'- commentID: String\l'
         r'- text: String\l'
         r'- timestamp: DateTime\l}',
         fillcolor='lightcyan', style='filled,rounded')

dot.node('DirectMessage',
         r'{DirectMessage|'
         r'- messageID: String\l'
         r'- content: String\l'
         r'- timestamp: DateTime\l'
         r'- isRead: boolean\l|'
         r'+ markAsRead()\l}',
         fillcolor='lightcyan', style='filled,rounded')


# --- Definisi Hubungan (Relationships) ---

# 1. Pewarisan (Generalization)
dot.edge('PhotoPost', 'Post', arrowhead='empty', label='inherits')
dot.edge('VideoPost', 'Post', arrowhead='empty', label='inherits')

# 2. Asosiasi (Association)
dot.edge('User', 'Account', arrowhead='none', dir='both', label='  manages', headlabel='1', taillabel='1')
dot.edge('User', 'Post', arrowhead='vee', dir='forward', label='  creates', headlabel='0..*', taillabel='1')
dot.edge('User', 'Story', arrowhead='vee', dir='forward', label='  creates', headlabel='0..*', taillabel='1')
dot.edge('User', 'Comment', arrowhead='vee', dir='forward', label='  writes', headlabel='0..*', taillabel='1')

# Asosiasi Recursif (Recursive Association)
dot.edge('User', 'User', arrowhead='vee', dir='forward', label='  follows', headlabel='0..*', taillabel='0..*')

# Asosiasi DM
dot.edge('DirectMessage', 'User', arrowhead='vee', label='from', headlabel='1' , taillabel='1..*')
dot.edge('DirectMessage', 'User', arrowhead='vee', label='to', headlabel='1..*' , taillabel='1')


# 3. Agregasi (Aggregation) - Post memiliki Comment
dot.edge('Comment', 'Post', arrowhead='diamond', dir='back', label='  has', headlabel='1', taillabel='0..*')

# 4. Dependensi (Dependency)
dot.edge('Post', 'ModerationService', style='dashed', arrowhead='vee', label='uses')
dot.edge('Comment', 'ModerationService', style='dashed', arrowhead='vee', label='uses')
dot.edge('Post', 'NotificationService', style='dashed', arrowhead='vee', label='triggers')
dot.edge('Comment', 'NotificationService', style='dashed', arrowhead='vee', label='triggers')
dot.edge('DirectMessage', 'NotificationService', style='dashed', arrowhead='vee', label='triggers')


# --- Render dan Simpan Diagram ---
try:
    # Path/nama file output, format 'png'
    output_filename = 'class_diagram'
    dot.render(output_filename, format='png', view=False, cleanup=True)
    print(f"Class diagram berhasil dibuat dan disimpan sebagai '{output_filename}.png'")
except graphviz.backend.execute.ExecutableNotFound:
    print("\n--- ERROR ---")
    print("Graphviz executable not found. Pastikan software Graphviz sudah terinstal dan path-nya benar.")
    print("Namun, file sumber 'class_diagram.dot' telah dibuat.")
    print("Anda bisa menyalin isinya dan menggunakan online editor untuk melihat diagramnya.")
except Exception as e:
    print(f"Terjadi error: {e}")