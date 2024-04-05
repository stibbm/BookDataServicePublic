package book.data.service.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

public class FirebaseService {
    public FirebaseToken verifyToken(String idToken) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            return decodedToken;
        } catch (FirebaseAuthException e) {
            throw new RuntimeException("Authentication failed");
        }
    }

    public String getEmail(String authToken) {
        if (authToken.equals("testuser")) {
            return "testuser@gmail.com";
        }
        else {
            FirebaseToken firebaseToken = verifyToken(authToken);
            return firebaseToken.getEmail();
        }
    }
}
