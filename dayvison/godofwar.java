package dayvison;

import robocode.*;
import java.awt.Color;

public class godofwar extends AdvancedRobot {
    // Variável para controlar o que fazer quando se iniciar a partida
    boolean inicio = true;
    boolean GunD = true;

    // Faz sempre:
    public void run() {
        // Cor do robô (RGB values for purple: R=128, G=0, B=128)
        setColors(new Color(128, 0, 128), new Color(128, 0, 128), new Color(128, 0, 128));
        while (true) {
            // Sempre gira o canhão se não estiver em outra função:
            if (GunD) {
                turnGunRight(160);
            } else {
                turnGunLeft(160);
            }
            // Mova-se aleatoriamente para evitar ser um alvo fácil
            setAhead(Math.random() * 400);
            setTurnRight(Math.random() * 360);
            execute();
        }
    }

    // Ao escanear um robô:
    public void onScannedRobot(ScannedRobotEvent e) {
        // Verifica se não é alguém do seu time, se sim, retorna:
        String name = e.getName();
        if (name.indexOf("G2M") != -1 || name.indexOf("Border") != -1) {
            return;
        }
        // Ajusta a mira para o inimigo
        double mira = normalRelativeAngle((e.getBearing() + (getHeading() - getRadarHeading())));
        // Dá o comando para o canhão virar em relação ao valor obtido pela função de normalizar ângulos e mira para o inimigo:
        turnGunRight(mira);
        // Atira com potência máxima
        fire(3);
        GunD = !GunD;
    }

    // Ao levar um tiro de um inimigo
    public void onHitByBullet(HitByBulletEvent e) {
        // Recua e vira aleatoriamente para escapar
        back(50);
        turnRight(Math.random() * 90);
    }

    // Quando bater em uma parede...
    public void onHitWall(HitWallEvent e) {
        // Vire para escapar da parede
        turnRight(90);
    }

    // Quando um tiro seu acertar
    public void onBulletHit(BulletHitEvent event) {
        // Comemore a acerto de tiro
    }

    // Quando a bala se perde (não acerta nenhum robô)
    public void onBulletMissed(BulletMissedEvent event) {
        // Continue sua estratégia
    }

    // Normalização dos ângulos
    public double normalRelativeAngle(double angle) {
        // Se o ângulo estiver entre -180° e 180° retorna o ângulo, por não ser preciso normalizar.
        if (angle > -180 && angle <= 180) {
            return angle;
        }
        // Cria uma nova variável para dar retorno com o novo valor.
        double fixedAngle = angle;

        // Enquanto menos que -180° adiciona 360° para normalizar o ângulo ao sistema.
        while (fixedAngle <= -180) {
            fixedAngle += 360;
        }
        // Enquanto maior que 180° diminui 360° para pegar o ângulo equivalente.
        while (fixedAngle > 180) {
            fixedAngle -= 360;
        }
        // Retorna o ângulo obtido.
        return fixedAngle;
    }

    // Se o Robô bater em um inimigo:
    public void onHitRobot(HitRobotEvent e) {
        // Recua e vira para escapar
        back(50);
        turnRight(90);
    }
}
