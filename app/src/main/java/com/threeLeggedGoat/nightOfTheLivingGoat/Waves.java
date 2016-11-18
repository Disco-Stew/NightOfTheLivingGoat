package com.threeLeggedGoat.nightOfTheLivingGoat;

import java.util.ArrayList;
import java.util.Random;

//Author Adam (Shaun provided instrumental support with the architecture of this class)
public class Waves {
    //Delay is in ticks
    //60 ticks to a second
    private int spawnDelay = 360;
    private int waveDelay = 360;

    private int waveCount = 1;

    private float specialChance = 15;
    private int bossSpawnPosition = 0;
    private int updates;

    private final int STARTING_WAVE_ENEMY_COUNT = 2;
    private final int ENEMY_COUNT_INCREASE = 1;
    private final int SPAWN_DELAY_DEDUCTION = 15;
    private final int SPAWN_DELAY_MIN = 30;
    private final int SPECIAL_CHANCE_MAX = 75;

    final int BOSS_SPAWN_STARTING_WAVE = 6;
    final int BOSS_SPAWN_MULTIPLE = 3;

    final int BASIC_ENEMY_DAMAGE = 1;
    final int TANK_DAMAGE = 2;
    final int SPRINT_DAMAGE = 2;
    final int BOSS_DAMAGE = 5;

    private final int TO_PLAYER_CENTER_X;
    private final int TO_PLAYER_CENTER_Y;

    private int enemyHealth = 10;

    private int enemyCount = STARTING_WAVE_ENEMY_COUNT;

    private boolean waveReset = false;
    private boolean resetUpdates = false;
    private boolean spawnBoss = false;
    private boolean bossWave = false;

    private Random rand = new Random();

    ArrayList<EnemyObject> enemies;
    ArrayList<ProjectileObject> projectiles;
    Assets assets;
    PlayerObject player;
    Viewport viewport;
    Scaler scaler;

    //Author Adam
    public Waves(ArrayList<EnemyObject> enemies, ArrayList<ProjectileObject> projectiles, Assets assets, PlayerObject player, Viewport viewport, Scaler scaler) {
        this.enemies = enemies;
        this.projectiles = projectiles;
        this.assets = assets;
        this.player = player;
        this.viewport = viewport;
        this.scaler = scaler;

        TO_PLAYER_CENTER_X = player.pistolWalkDownLeft.getImage(0).getWidth() / 4;
        TO_PLAYER_CENTER_Y = player.pistolWalkDownLeft.getImage(0).getHeight() / 4;
    }

    //Author Adam
    public void update() {
        updates++;

        // Resets the internal update value when an event is fired
        if (isResetUpdates()) {
            updates = 0;
            setResetUpdates(false);
        }

        //Ensures a boss is spawned at some point during the current wave if the criteria are met
        if (isBossWave()) {
            if (!spawnBoss) {
                spawnBoss = true;
                bossSpawnPosition = rand.nextInt(enemyCount) + 1;
            }
        }

        //Ensures the delay between waves is maintained by resetting the updates value when a wave ends
        if (isWaveEnded() && !waveReset) {
            waveReset = true;
            resetUpdates = true;
        }

        if (isWaveEnded() && (isWaveDelayOver(updates)) && waveReset) {
            spawnBoss = false;
            newWave();
            waveReset = false;
            resetUpdates = true;
        } else if (isSpawnNewEnemy(updates)) {
            spawnEnemy();
            resetUpdates = true;
        }
    }

    //Increments game difficulty and starts a new wave with a new enemy
    //Author Adam
    public void newWave() {
        enemyCount = STARTING_WAVE_ENEMY_COUNT + (ENEMY_COUNT_INCREASE * waveCount);

        //If decreasing the spawn delay doesn't make it smaller than the minimum
        if ((spawnDelay - SPAWN_DELAY_DEDUCTION) > SPAWN_DELAY_MIN) {
            spawnDelay -= SPAWN_DELAY_DEDUCTION;
        }
        //If increasing the special chance doesn't make it larger than the maximum
        if ((specialChance + SPAWN_DELAY_DEDUCTION) < SPECIAL_CHANCE_MAX) {
            specialChance += SPAWN_DELAY_DEDUCTION;
        }
        waveCount += 1;
        bossWave = (waveCount >= BOSS_SPAWN_STARTING_WAVE) && (waveCount % BOSS_SPAWN_MULTIPLE == 0);
        spawnEnemy();
    }

    //Author Stuart
    public void resetWave() {
        waveReset = true;
        enemyCount = STARTING_WAVE_ENEMY_COUNT + (ENEMY_COUNT_INCREASE * waveCount);
    }

    //Determines type of enemy spawned based on the wave number and special enemy spawn chance
    //Author Adam
    public void spawnEnemy() {
        if (spawnBoss && enemyCount == bossSpawnPosition ) {
            createBoss();
        } else {
            if (waveCount < 3) {
                createBasicEnemy();
            } else if (waveCount < 5) {
                if (specialChance >= rand.nextInt(101)) {
                    createTank();
                } else {
                    createBasicEnemy();
                }
            } else {
                if (specialChance >= rand.nextInt(101)) {
                    //equal chance of either special enemy type spawning
                    switch (rand.nextInt(2)) {
                        case 0:
                            createSprint();
                            break;
                        case 1:
                            createTank();
                            break;
                    }
                } else {
                    createBasicEnemy();
                }
            }
        }
        enemyCount -= 1;
    }

    //Author Adam
    private void createBasicEnemy() {
        enemies.add(new BasicEnemy(assets, player, TO_PLAYER_CENTER_X, TO_PLAYER_CENTER_Y, enemyHealth, BASIC_ENEMY_DAMAGE, viewport, scaler));
    }

    //Author Adam
    private void createTank() {
        enemies.add(new TankEnemy(assets, player, projectiles, TO_PLAYER_CENTER_X, TO_PLAYER_CENTER_Y, enemyHealth, TANK_DAMAGE, viewport, scaler));

    }

    //Author Adam
    private void createSprint() {
        enemies.add(new SprintEnemy(assets, player, projectiles, TO_PLAYER_CENTER_X, TO_PLAYER_CENTER_Y, enemyHealth, SPRINT_DAMAGE, viewport, scaler));
    }

    //Author Adam
    private void createBoss() {
        enemies.add(new Boss(assets, player, projectiles, TO_PLAYER_CENTER_X, TO_PLAYER_CENTER_Y, enemyHealth, BOSS_DAMAGE, viewport, scaler));
    }
    //Author Adam
    public int getEnemyCount() {
        return (enemyCount);
    }

    //Author Adam
    public boolean isWaveDelayOver(int updates) {
        return (updates > waveDelay);
    }

    //Author Adam
    public boolean isSpawnNewEnemy(int updates) {return (updates > spawnDelay) && (enemyCount > 0);}

    //Author Adam
    private boolean isWaveEnded() {return (getEnemyCount() == 0) && (enemies.isEmpty());}

    //Author Adam
    public boolean isBossWave(){ return bossWave;}

    //Author Adam
    public void setResetUpdates(boolean resetUpdates) {this.resetUpdates = resetUpdates;}

    //Author Adam
    public boolean isResetUpdates() {return resetUpdates;}

    //Author Adam
    public int getWaveCount() {
        return waveCount;
    }
}
