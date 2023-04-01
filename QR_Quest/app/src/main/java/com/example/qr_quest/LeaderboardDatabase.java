package com.example.qr_quest;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardDatabase {

    public LeaderboardDatabase(){}

    public static void getAllUsersByPoints(OnSuccessListener<List<User>> listener) {
        List<User> userList = new ArrayList<>();
        FirebaseFirestore.getInstance().collection("Users")
                .orderBy("score", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        int rank = 0;
                        long prevScore = -1;
                        for (QueryDocumentSnapshot userDoc : task.getResult()) {
                            long currentScore = userDoc.getLong("score");
                            if (currentScore != prevScore) {
                                rank++;
                                prevScore = currentScore;
                            }

                            User user = new User();
                            user.setUsername(userDoc.getString("user_name"));
                            user.setEmail(userDoc.getString("email"));
                            user.setFirstName(userDoc.getString("first_name"));
                            user.setLastName(userDoc.getString("last_name"));
                            user.setPhoneNumber(userDoc.getString("phone"));

                            user.setQRCodeList((ArrayList<String>) userDoc.get("qr_code_list"));
                            user.setScore(userDoc.getLong("score"));
                            user.setPointsRank(rank);
                            userList.add(user);
                        }
                        listener.onSuccess(userList);
                    } else {
                        Log.d(TAG, "Error getting User documents: ", task.getException());
                        listener.onSuccess(userList);
                    }
                });
    }

    public static void getAllUsersByQRNums(OnSuccessListener<List<User>> listener) {
        List<User> userList = new ArrayList<>();
        FirebaseFirestore.getInstance().collection("Users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot userDoc : task.getResult()) {
                            User user = new User();
                            user.setUsername(userDoc.getString("user_name"));
                            user.setEmail(userDoc.getString("email"));
                            user.setFirstName(userDoc.getString("first_name"));
                            user.setLastName(userDoc.getString("last_name"));
                            user.setPhoneNumber(userDoc.getString("phone"));

                            user.setQRCodeList((ArrayList<String>) userDoc.get("qr_code_list"));
                            user.setScore(userDoc.getLong("score"));
                            userList.add(user);
                        }
                        // Sort the user list by the length of the qr_code_list field
                        userList.sort((user1, user2) -> user2.getQRCodes().size() - user1.getQRCodes().size());

                        int rank = 0;
                        int prevQRNum = -1; // initialize to a value lower than any possible number of QR codes
                        for (User user : userList) {
                            int currentQRNum = user.getQRCodes().size();
                            if (currentQRNum != prevQRNum) {
                                rank++;
                                prevQRNum = currentQRNum;
                            }
                            user.setQRNumRank(rank); // Set the rank attribute based on the current rank value
                        }

                        listener.onSuccess(userList);
                    } else {
                        Log.d(TAG, "Error getting User documents: ", task.getException());
                        listener.onSuccess(userList);
                    }
                });
    }

    public static void getAllQRsByScore(OnSuccessListener<List<QR>> listener) {
        QRDatabase.getAllQRs(qrList -> {
            qrList.sort((qr1, qr2) -> qr2.getScore().compareTo(qr1.getScore()));

            int rank = 0;
            long prevScore = -1; // initialize to a value lower than any possible score
            for (QR qr : qrList) {
                long currentScore = qr.getScore();
                if (currentScore != prevScore) {
                    rank++;
                    prevScore = currentScore;
                }
                qr.setRank(rank);
            }

            listener.onSuccess(qrList);
        });
    }
}
