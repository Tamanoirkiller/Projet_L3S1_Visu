

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Thread qui g�re l'envoi des donn�es au serveur
 * @autor Ludovic BURG
 */
public class ReceptionThread extends Thread implements Runnable {
    private ProtocolManager protocolManager;
    private LocalisationArbrePanel localisationArbrePanel;
    private boolean running = true;
    private HashMap<String, Capteur> capteurs = new HashMap<>();

    public ReceptionThread(ProtocolManager protocolManager, LocalisationArbrePanel localisationArbrePanel) {
        this.protocolManager = protocolManager;
        this.localisationArbrePanel = localisationArbrePanel;
    }

    @Override
    public void run() {
        String recu;
        String type;
        Capteur tmp;
        try {
            while (this.running) {
                recu = this.protocolManager.receptionVisu();
                type = this.protocolManager.getTypeOfReceivedMessage(recu);
                if (!recu.equals("pas connecte")) {
                    if (type.equals("CapteurPresent")) { /* Reception d'un message destiner a mettre a jour l'arbre */
                        if (!this.protocolManager.getFieldFromReceivedMessage(6, recu).equals(";erreur;")) {
                            tmp = new Capteur(
                                        this.protocolManager.getFieldFromReceivedMessage(1, recu),
                                        this.protocolManager.getFieldFromReceivedMessage(2, recu),
                                        this.protocolManager.getFieldFromReceivedMessage(3, recu),
                                        this.protocolManager.getFieldFromReceivedMessage(4, recu),
                                        this.protocolManager.getFieldFromReceivedMessage(5, recu),
                                        this.protocolManager.getFieldFromReceivedMessage(6, recu)
                                    );
                            this.capteurs.put(this.protocolManager.getFieldFromReceivedMessage(1, recu), tmp);
                            this.localisationArbrePanel.addCapteurInt(
                                    this.protocolManager.getFieldFromReceivedMessage(3, recu),
                                    this.protocolManager.getFieldFromReceivedMessage(4, recu),
                                    this.protocolManager.getFieldFromReceivedMessage(5, recu),
                                    this.protocolManager.getFieldFromReceivedMessage(1, recu)
                            );
                        } else {
                            this.localisationArbrePanel.addCapteurExt(
                                    Double.parseDouble(this.protocolManager.getFieldFromReceivedMessage(3, recu)),
                                    Double.parseDouble(this.protocolManager.getFieldFromReceivedMessage(4, recu))
                            );
                        }
                    } else if (type.equals("InscriptionCapteurKO")) {

                    } else if (type.equals("DesinscriptionCapteurKO")) {

                    } else if (type.equals("ValeurCapteur")) { /* Reception d'un message destiné a mettre à jour le tableau */

                    } else if (type.equals("CapteurDeco")) { /* reception d'un message destiné à mettre à jour l'arbre  */

                    }
                }
            }
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isRunning() {
        return running;
    }
}
