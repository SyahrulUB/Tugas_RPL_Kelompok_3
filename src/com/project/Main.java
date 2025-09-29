package com.project;

import java.net.MalformedURLException;
import java.net.URI;

public class Main {
    public static void main(String[] args) {
        try {
            // create users
            User kiki = new User("u1", "Kiki", URI.create("http://example.com/kiki.png").toURL());
            User nada = new User("u2", "Nada", URI.create("http://example.com/nada.png").toURL());
            User syahrul = new User("u3", "Syahrul", URI.create("http://example.com/syahrul.png").toURL());

            // create accounts and associate
            Account accKiki = new Account("kiki", "kiki@example.com", "pass1");
            accKiki.setOwner(kiki);
            kiki.setAccount(accKiki);

            Account accNada = new Account("nada", "nada@example.com", "pass2");
            accNada.setOwner(nada);
            nada.setAccount(accNada);

            // Kiki posts a photo
            PhotoPost photo = new PhotoPost("p1", "Hello world", "http://example.com/img.jpg", "none");
            Comment comm1 = new Comment("c1", "Nice pic!");
            photo.addComment(comm1);
            photo.addLike(nada);
            photo.addLike(syahrul);

            // Print a tidy summary
            System.out.println("=== Users ===");
            System.out.println("- " + kiki);
            System.out.println("- " + nada);
            System.out.println("- " + syahrul);

            System.out.println("\n=== Posts ===");
            System.out.println("Photo: " + photo);
            System.out.println("  Comments: " + photo.getComments());
            System.out.println("  Likes: " + photo.getLikes());

            // interactions
            kiki.followUser(nada);
            nada.followUser(syahrul);
            System.out.println("\n=== Interactions ===");
            System.out.println("Kiki follows: " + kiki);

            DirectMessage dm = new DirectMessage("m1", "Halo Nada, ayo bertemu", kiki, nada);
            dm.sendMessage();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
