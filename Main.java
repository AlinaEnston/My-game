import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
//импорт для дальнейшей разработки

//класс для характеристик моего персонажа
class Character {
    protected String name;
    protected int health;
    protected int strength;
    protected int dexterity;

    public Character(String name, int health, int strength, int agility) {
        this.name = name;
        this.health = health;
        this.strength = strength;
        this.dexterity = agility;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
    }

    public int attack() {
        return strength;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }
}

class Player extends Character {
    private int experience;
    private int level;
    private int gold;

    public Player(String name) {
        super(name, 100, 10, 5);
        this.experience = 0;
        this.level = 1;
        this.gold = 0;
    }

    public void gainExperience(int exp) {
        experience += exp;
        if (experience >= level * 100) {
            levelUp();
        }
    }
//мотивация повышения уровня в том, что мы повышаем выносливость и силу героя
    private void levelUp() {
        level++;
        health += 20; // Увеличиваем здоровье при повышении уровня
        strength += 5; // Увеличиваем силу при повышении уровня
        System.out.println(name + " поднялась на уровень " + level + "!");
    }

    public void earnGold(int amount) {
        gold += amount;
    }

    public void buyPotion() {
        if (gold >= 10) {
            health += 20; // Лечим игрока на 20 единиц
            gold -= 10;
            System.out.println(name + " купила зелье за чеканные монеты и восстановил 20 здоровья!");
        } else {
            System.out.println("Недостаточно золота для покупки зелья! Иди и убей еще монстров");
        }
    }

    public int getGold() {
        return gold;
    }
}

class Monster extends Character {
    public Monster(String name, int health, int strength, int agility) {
        super(name, health, strength, agility);
    }
}

class Game {
    private Player player;
    private List<Monster> monsters;

    public Game() {
        player = new Player("рыцарь Алина");
        monsters = new ArrayList<>();
        monsters.add(new Monster("Скелетоног", 50, 5, 2));
        monsters.add(new Monster("Гоблин-ниндзя", 70, 7, 3));
        monsters.add(new Monster("Дракон", 100, 10, 5));
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        String command;

        while (true) {
            System.out.println("Введите команду (В темный лес, К торговцу, На выход ): ");
            command = scanner.nextLine();

            switch (command) {
                case "В темный лес":
                    fight();
                    break;
                case "К торговцу":
                    player.buyPotion();
                    break;
                case "На выход":
                    System.out.println("Выход из игры. Возвращайся)");
                    return;
                default:
                    System.out.println("Неизвестная команда.");
            }
        }
    }

    private void fight() {
        if (monsters.isEmpty()) {
            System.out.println("Все монстры побеждены!Ты справилась!!");
            return;
        }

        Monster monster = monsters.get(0);
        System.out.println("Вы сошлись в схватке с " + monster.getName());

        while (player.isAlive() && monster.isAlive()) {
            monster.takeDamage(player.attack());
            System.out.println(player.getName() + " яростно атакует " + monster.getName() + " и своим оружием наносит " + player.attack() + " урона.");
            System.out.println(monster.getName() + " осталось здоровья: " + monster.getHealth());

            if (monster.isAlive()) {
                player.takeDamage(monster.attack());
                System.out.println(monster.getName() + " бежит с огнем в глазах на рыцаря, атакует  " + player.getName() + " и наносит без жалости  " + monster.attack() + " урона.");
                System.out.println(player.getName() + " осталось здоровья: " + player.getHealth());
            }
        }

        if (player.isAlive()) {
            System.out.println("Вы уничтожили " + monster.getName() + "!");
            player.gainExperience(50); // Пример получения опыта
            player.earnGold(20); // Пример получения золота
            System.out.println("Вы получили 50 опыта и 20 золота!");
            monsters.remove(0); // Удаляем побежденного монстра
        } else {
            System.out.println("потрачено...(убита)");
        }
    }
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}
