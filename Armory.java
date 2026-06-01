import java.util.List;

/**
 * The weapon wheels offered at each stage of the journey. Tier 1 is presented during
 * character creation; each later tier is unlocked after a milestone boss. Every wheel holds
 * five weapons (the constructor takes name, light, heavy and special attack names, the five
 * stat-scaling factors, then price, base damage and attack time).
 */
final class Armory {
    private Armory() {}

    static List<Weapon> tier1() {
        return List.of(
            new Weapon("Greatsword, scales with Strength primarily and Dexterity secondarily", "Swing", "Slice", "Lion's Claw", new double[]{2, 0.5, 0, 0, 0}, 0, 400, 4),
            new Weapon("Urumi, scales with Dexterity primarily and Strength secondarily", "Whip", "Trip", "Hack'n'Slash", new double[]{0.5, 2, 0, 0, 0}, 0, 150, 1),
            new Weapon("Glintstone Staff, scales with Intelligence", "Glintstone Pebble", "Comet", "Comet Azur", new double[]{0, 0, 2, 0, 0}, 0, 100, 2),
            new Weapon("Winged Scythe, scales with Intelligence and Faith primarily and Strength secondarily", "Scythe", "Sweep", "Death Scythe", new double[]{0, 0, 1, 1, 1}, 0, 300, 5),
            new Weapon("Rivers of Blood, scales with Arcane primarily and Dexterity secondarily", "Bloodletting", "Stab", "Unsheath", new double[]{0, 0.5, 0, 0, 1}, 0, 200, 2)
        );
    }

    static List<Weapon> tier2() {
        return List.of(
            new Weapon("Great Club, scales with Strength primarily and Dexterity secondarily", "Smash", "Crush", "Earthquake", new double[]{3.0, 0.5, 0.0, 0.0, 0.0}, 20, 500, 5),
            new Weapon("Reduvia, scales with Dexterity primarily and Arcane secondarily", "Stab", "Flay", "Blood Surge", new double[]{0.5, 3.0, 0.0, 0.0, 0.5}, 20, 250, 2),
            new Weapon("Azur's Glintstone Staff, scales with Intelligence primarily", "Magic Missile", "Arcane Burst", "Meteor Shower", new double[]{0.0, 0.0, 3.0, 0.0, 0.0}, 20, 300, 3),
            new Weapon("Godslayer Sword, scales with Faith primarily and Dexterity secondarily", "Cleave", "Searing Strike", "Divine Retribution", new double[]{0.5, 0.0, 0.0, 3.0, 0.0}, 20, 400, 4),
            new Weapon("Death's Poker, scales with Arcane primarily and Dexterity secondarily", "Pierce", "Spectral Thrust", "Soul Rend", new double[]{0.0, 1.0, 0.0, 0.0, 3.0}, 20, 350, 3)
        );
    }

    static List<Weapon> tier3() {
        return List.of(
            new Weapon("Colossal Greatsword, scales with Strength primarily and a bit of Dexterity", "Heavy Swing", "Ground Slam", "Titan's Wrath", new double[]{3.5, 0.7, 0.0, 0.0, 0.0}, 700, 600, 6),
            new Weapon("Silence, scales with Dexterity primarily and Arcane secondarily", "Quick Slash", "Veil Slice", "Silent Execution", new double[]{0.7, 3.5, 0.0, 0.0, 0.7}, 500, 300, 3),
            new Weapon("Moonlight Greatsword, scales with Intelligence primarily", "Lunar Strike", "Starfall", "Cosmic Burst", new double[]{0.0, 0.0, 4.0, 0.0, 0.0}, 700, 400, 4),
            new Weapon("Blasphemous Blade, scales with Faith primarily and Dexterity secondarily", "Scorch", "Blaze Swipe", "Hellfire", new double[]{0.6, 0.3, 0.0, 4.0, 0.0}, 650, 450, 5),
            new Weapon("Mimic Tear's Blade, scales with Arcane primarily and Dexterity secondarily", "Copycat Strike", "Shadow Edge", "Mirrored Death", new double[]{0.0, 1.2, 0.0, 0.0, 4.0}, 550, 400, 4)
        );
    }

    static List<Weapon> tier4() {
        return List.of(
            new Weapon("Grafted Blade Greatsword, scales with Strength primarily and a bit of Dexterity", "Overhead Smash", "Sweep", "Mighty Slam", new double[]{3.8, 0.6, 0.0, 0.0, 0.0}, 700, 600, 6),
            new Weapon("Eclipse Shotel, scales with Dexterity primarily and a bit of Strength", "Slice", "Sun's Flare", "Eclipse Cut", new double[]{0.8, 3.0, 0.0, 0.0, 0.0}, 550, 300, 3),
            new Weapon("Sword of Night and Flame, scales with Intelligence primarily and Faith secondarily", "Night Slash", "Flame Sweep", "Starfire", new double[]{0.5, 0.2, 3.0, 3.0, 0.0}, 750, 450, 4),
            new Weapon("Godslayer Greatsword, scales with Faith primarily and a bit of Dexterity", "Sacred Swing", "Holy Cleave", "God's Judgement", new double[]{0.6, 0.3, 0.0, 3.5, 0.0}, 650, 400, 5),
            new Weapon("Night's Sacred Blade, scales with Arcane primarily and Dexterity secondarily", "Dark Slash", "Shadow Stab", "Moonlit Veil", new double[]{0.0, 1.2, 0.0, 0.0, 4.0}, 600, 350, 4)
        );
    }

    static List<Weapon> tier5() {
        return List.of(
            new Weapon("Great Club, scales with Strength primarily and a bit of Dexterity", "Smash", "Crush", "Earthquake", new double[]{4.0, 0.8, 0.0, 0.0, 0.0}, 800, 700, 6),
            new Weapon("Bloodhound's Fang, scales with Dexterity primarily and a bit of Arcane", "Slash", "Bloodletting", "Fang Strike", new double[]{0.8, 3.0, 0.0, 0.0, 1.0}, 700, 350, 4),
            new Weapon("Moonveil, scales with Intelligence primarily and Dexterity secondarily", "Lunar Slash", "Starfall", "Moonburst", new double[]{0.5, 0.6, 4.0, 0.0, 0.0}, 800, 500, 4),
            new Weapon("Eclipse Shotel, scales with Dexterity primarily and a bit of Strength", "Cut", "Sun Ray", "Eclipse Strike", new double[]{0.9, 3.5, 0.0, 0.0, 0.0}, 650, 350, 3),
            new Weapon("Dark Moon Greatsword, scales with Intelligence primarily and a bit of Faith", "Lunar Slash", "Cosmic Ray", "Dark Moon Beam", new double[]{0.0, 0.0, 5.0, 1.0, 0.0}, 850, 600, 5)
        );
    }

    static List<Weapon> tier6() {
        return List.of(
            new Weapon("Grafted Blade Greatsword, scales with Strength primarily and some Dexterity", "Overhead Smash", "Heavy Cleave", "Titan's Wrath", new double[]{5.0, 1.0, 0.0, 0.0, 0.0}, 1000, 800, 8),
            new Weapon("Reduvia, scales with Dexterity primarily and some Arcane", "Stab", "Flay", "Blood Surge", new double[]{0.8, 3.2, 0.0, 0.0, 1.5}, 900, 500, 5),
            new Weapon("Carian Regal Scepter, scales with Intelligence primarily and some Faith", "Mystic Bolt", "Arcane Wave", "Regal Barrage", new double[]{0.3, 0.2, 4.8, 1.2, 0.0}, 950, 650, 5),
            new Weapon("Godslayer's Greatsword, scales with Faith primarily and some Dexterity", "Holy Cleave", "Divine Strike", "God's Wrath", new double[]{0.6, 1.0, 0.0, 4.2, 0.0}, 850, 700, 5),
            new Weapon("Black Knife, scales with Arcane primarily and some Dexterity", "Shadow Stab", "Silent Cut", "Blackened Blade", new double[]{0.0, 1.7, 0.0, 0.0, 4.0}, 800, 400, 4)
        );
    }

}
