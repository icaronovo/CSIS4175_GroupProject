const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();

exports.notifyOnNewSighting = functions.firestore
  .document('sightings/{sightingId}')
  .onCreate(async (snapshot, context) => {
    const newSighting = snapshot.data();

    // Retrieve FCM tokens from Firestore (replace 'users' with your collection)
    const tokensSnapshot = await admin.firestore().collection('userTokens').get();
    const tokens = tokensSnapshot.docs.map(doc => doc.data().fcmToken);

    // Send a notification to all devices
    const payload = {
      notification: {
        title: 'New Sighting',
        body: 'A new sighting has been added!',
      },
    };

    await admin.messaging().sendToDevice(tokens, payload);

    return null;
  });