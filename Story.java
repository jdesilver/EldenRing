import java.util.List;

/**
 * Every line of the game's narration, in the order the player meets it. Each constant is one
 * scene: a list of beats, where the game pauses for the player to press Enter between beats.
 * Splitting the prose out of {@link Main} keeps the story readable and the flow code uncluttered.
 */
final class Story {
    private Story() {}

    static final List<String> INTRO = List.of(
        "In the beginning, there was only a single Erdtree.",
        "Until one day, an eternal dropped from the Erdtree.",
        "By nature, all Eternals seek to overthrow the Erdtree and destroy all others.",
        "It is said that when a Tarnished ascends to divinity, they shall wreak havoc and chaos upon all.",
        "At long last, you are that Tarnished.",
        "Collect your Rune Fragments, level your strength, and collect the weapons you desire.",
        "You will need all the power you can muster to overthrow the Erdtree.",
        "The lands beyond the Erdtree are fraught with peril, and only those of great fortitude and cunning will prevail."
    );

    static final List<String> TUTORIAL = List.of(
        "Tutorial",
        "1. Combos",
        "   - Combos are sequences of attacks executed by bosses.",
        "   - Each combo has a specific order and timing.",
        "   - Pay attention to the attack patterns and prepare to dodge or counter.",
        "2. Timing",
        "   - Boss attacks are divided into phases: charge-up, attack, and cooldown.",
        "   - During the charge-up phase, you have time to react and prepare.",
        "   - React quickly during the attack phase to avoid damage.",
        "   - After the attack, the boss will enter a cooldown phase where they are vulnerable.",
        "3. Types of Attacks",
        "   - Light Attack: Fast but less powerful. Useful for quick hits.",
        "   - Heavy Attack: Slower but more powerful. Can break through defenses.",
        "   - Special Attack: Fast and more powerful. Costs Focus.",
        "   - Heal: You can heal HP or FP during combat .",
        "   - Dodge: Use the dodge feature to evade attacks. You can dodge in two specific directions.",
        "   - Inputs: If you ever input anything that isn't an option, you will be sent back to the original decison.",
        "4. Scaling",
        "   - Your attacks and damage scale with your stats.",
        "   - The more you level up and allocate runes to your stats, the stronger your attacks become.",
        "   - Bosses also have their damage scaling based on their level and phase."
    );

    static final List<String> CREATION_COMPLETE = List.of(
        "You have completed the character creation process.");

    static final List<String> JOURNEY_TO_MARGIT = List.of(
        "Good luck, Tarnished.",
        "You begin your journey in a world where the shadows of past glories linger, haunted by forgotten heroes and lost legends.",
        "As you traverse the crumbling ruins and treacherous landscapes, you will encounter myriad foes and allies, each with their own motives and secrets.",
        "Some will seek to aid you in your quest, offering wisdom and guidance, while others will test your resolve, eager to see you falter.",
        "Through trials and tribulations, your strength will grow, and your skills will be honed in the crucible of battle.",
        "One such trial awaits you at the bridge that leads to the castle's threshold - a test of your mettle against a formidable adversary.",
        "He is known as Margit, the Fell Omen, a guardian of the path to greater power and the threshold to the true depths of your destiny.",
        "Margit wields arcane sorcery and devastating melee strikes, a formidable challenge for any who dare to seek the Erdtree's throne.",
        "Prepare yourself for the fight ahead, for Margit will not yield easily. His presence signifies the first true test of your ascension.",
        "Embrace the power within you, sharpen your weapons, and steady your heart. The battle to come will define your path forward.",
        "With each clash of steel and surge of magic, you will edge closer to the Erdtree's glory - or be cast into darkness.",
        "The heavy mist swirling around the bridge thickens as you approach the castle's looming gates.",
        "The sky above darkens, casting an ominous shadow over the landscape. With each step, the distant thunder rumbles, echoing your racing heartbeat.",
        "At the end of the bridge stands Margit, the Fell Omen, a figure of imposing stature and formidable presence.",
        "His eyes, glowing with arcane fury, lock onto you as you draw closer. The air crackles with latent magic, and his cloak flutters like a storm in anticipation.",
        "Margit's voice, like gravel grinding on stone, pierces the silence. 'Foul Tarnished, in search of the Elden Ring. Emboldened by the flame of ambition. Someone must extinguish thy flame. Let it be Margit the Fell'",
        "He raises his staff high, and the ground trembles as dark energy begins to coalesce around him.",
        "His weapons - enchanted with eldritch power - shine menacingly under the stormy sky.",
        "The bridge beneath you groans, ready to bear witness to the clash that will determine your fate.",
        "With a final, defiant glance, Margit prepares for the battle that will test every ounce of your strength and skill.",
        "It's time. Face your fears, summon your resolve, and step into the crucible of combat. The fate of your journey - and perhaps the very world - rests on this moment."
    );

    static final List<String> AFTER_MARGIT = List.of(
        "The clash with Margit has left the bridge in ruins, and the air is thick with the remnants of magic and the echoes of battle.",
        "As the dust settles and the mist begins to clear, you stand victorious but weary. Your body aches from the exertion, and your heart still races from the fight.",
        "From the shadows of the crumbling castle, a figure emerges - a woman of ethereal grace and quiet strength. She moves with an air of calm assurance, her presence a stark contrast to the chaos that just transpired.",
        "Well fought, Tarnished, she says softly, her voice carrying a soothing melody amidst the remnants of the storm. I am Melina, and I have come to aid you on your journey.",
        "She approaches you with a warm, reassuring smile. The path ahead is fraught with even greater dangers and challenges. You have proven your worth, but the road to the Erdtree will test you further.",
        "Melina extends her hand, and as she does, a soft, golden light begins to emanate from the ground nearby.",
        "Come, she beckons, there is a Site of Grace where you may find respite and guidance. It will restore your strength and allow you to prepare for the trials yet to come.",
        "You follow Melina to the Site of Grace, a serene haven amid the desolation. The site is marked by a gentle, radiant light that seems to soothe the very essence of your being.",
        "As you approach, the light envelops you, and you feel a profound sense of peace and renewal. Your wounds heal, your spirit is lifted, and your resolve is strengthened.",
        "Rest here, Melina advises. The grace of this place will provide you with clarity and insight. Use it to reflect on your journey and to prepare for the path that lies ahead.",
        "With the Site of Grace to guide you, you take a moment to catch your breath, knowing that the true depth of your destiny awaits beyond the horizon.",
        "Melina's presence remains a comforting assurance as you settle into the tranquil light, ready to face the challenges that will come with renewed vigor and determination.",
        "When you are ready, Melina says softly, I will be here to guide you. The road is long, but you are not alone.",
        "With that, she steps back into the shadows, leaving you to contemplate your next move as the light of the Site of Grace casts a warm glow around you.",
        "The journey continues, and with each step, the path to the Erdtree becomes clearer, illuminated by the strength and wisdom you have gained."
    );

    static final List<String> BEFORE_GODRICK = List.of(
        "As you recover from the fierce battle with Margit, the Grafted Castle comes into view. Its towering spires and dark, looming presence reflect the harshness of its inhabitants. The very air around it seems thick with the weight of countless battles fought and lost.",
        "Melina walks beside you, her gaze steady as she surveys the castle's twisted silhouette. 'This place is steeped in sorrow and despair,' she says. 'Godrick the Grafted, once a noble warrior, has become a grotesque parody of his former self. His power is both fearsome and corrupting.'",
        "You navigate the castle's foreboding corridors, the silence broken only by the distant clamor of rusted armor and the soft echoes of unseen creatures. The walls, once grand, are now lined with grim trophies and macabre relics, hinting at the horrors within.",
        "Entering the throne room, you are confronted by Godrick, his massive, grafted body a monstrous amalgamation of metal and flesh. His eyes, burning with malevolent fury, fixate on you as he roars in defiance.",
        "Mighty Dragon, thou'rt a trueborn heir. Lend me thy strength, o kindred. Deliver me unto greater heights. ...Well. A lowly Tarnished, playing as a lord. I command thee, kneel! I am the lord of all that is golden!",
        "The battle with Godrick is a harrowing ordeal. Each of his blows is delivered with an overwhelming force, the weight of his weapon sending tremors through the castle's very foundation. His strength and resilience are matched only by his cunning and brutality."
    );

    static final List<String> AFTER_GODRICK = List.of(
        "Melina's tactical advice proves invaluable as you struggle against Godrick's onslaught. Her insights help you to dodge and counter his devastating attacks, and through sheer determination and skill, you manage to overcome the Grafted King.",
        "The throne room falls silent, the echoes of your battle lingering in the air. As Godrick's massive form crumbles, Melina approaches with a look of relief. 'We have triumphed over a significant foe,' she says. 'But our journey is far from over. The path ahead leads us to the shattered remnants of the Academy of Raya Lucaria.'"
    );

    static final List<String> BEFORE_RENNALA = List.of(
        "Your journey leads you to the Academy of Raya Lucaria, a place of ancient magic and long-forgotten knowledge. The academy's once-majestic halls now lie in ruin, a haunting testament to its past grandeur.",
        "Melina guides you through the desolate grounds, her presence a beacon of calm in the midst of the academy's eerie silence. 'Rennala, Queen of the Full Moon, resides within these ruins,' she says. 'Her mastery of sorcery is formidable, and her wrath is feared by all who enter her domain.'",
        "As you navigate through the crumbling corridors and shattered classrooms, the very air seems to hum with the remnants of powerful spells. The walls are adorned with arcane symbols and magical artifacts, their glow casting flickering shadows on the debris-strewn floor.",
        "Hush, little culver. I'll soon birth thee anew, a sweeting fresh and pure...",
        "Entering the grand chamber of the academy, Rennala appears, her form wreathed in a luminous aura. The Queen of the Full Moon, her eyes glowing with arcane power, exudes an air of regal authority and danger.",
        "The battle with Rennala is a mesmerizing spectacle of sorcery and arcane might. Her attacks are both beautiful and deadly, weaving intricate patterns of magic that challenge your every move. The room becomes a chaotic dance of light and dark as she unleashes her full power."
    );

    static final List<String> AFTER_RENNALA = List.of(
        "With Melina's strategic guidance and your own resilience, you navigate the storm of spells and find the moments to strike. Rennala's defeat brings a profound silence, her ethereal form dissipating into the ether. The academy, though still in ruins, feels a little lighter for your victory.",
        "Melina approaches, her gaze filled with a mix of satisfaction and concern. 'Rennala's fall is a crucial step in our journey,' she says. 'Yet the road ahead will only grow more treacherous. Prepare yourself for the trials that lie beyond.'"
    );

    static final List<String> BEFORE_RED_WOLF = List.of(
        "The path now takes you to the treacherous realm of the Red Wolf of Radagon. The landscape is a bleak and desolate wasteland, scorched by fiery eruptions and littered with the remnants of ancient battles.",
        "Melina remains a steady guide as you traverse the harsh terrain. 'The Red Wolf of Radagon is a swift and relentless adversary,' she warns. 'His attacks are quick and deadly, and his mastery of combat will test your agility and reflexes.'",
        "As you reach the arena, the ground quakes with the Red Wolf's ferocious roars. The beast, its fur bristling with dark energy, emerges from the shadows, its movements a blur of speed and ferocity.",
        "The battle with the Red Wolf is a relentless test of agility and strategy. His attacks are fast and unpredictable, forcing you to constantly move and adapt. Melina's tactical advice helps you anticipate his movements and find the right moments to strike."
    );

    static final List<String> AFTER_RED_WOLF = List.of(
        "After an intense and exhausting fight, you manage to defeat the Red Wolf, his form collapsing into a pool of dark energy. The battlefield falls silent, the oppressive atmosphere lifting slightly as you catch your breath.",
        "Melina approaches, her expression a mixture of relief and encouragement. 'You've faced another formidable challenge and emerged victorious,' she says. 'But the journey is far from over. The next trial will be even more daunting - the domain of Rykard, the God-Devouring Serpent.'"
    );

    static final List<String> BEFORE_SERPENT = List.of(
        "Your journey now leads you into the heart of darkness, the domain of Rykard, the God-Devouring Serpent. The landscape is a grotesque vision of twisted flesh and dark, pulsating energy, creating a nightmarish environment.",
        "Melina's guidance is a beacon of hope as you navigate through the nightmarish surroundings. 'Rykard's domain is a place of immense power and corruption,' she warns. 'His form is both terrifying and blasphemous. Prepare yourself for a battle of epic proportions.'",
        "As you enter the cavernous lair, Rykard's colossal serpentine form emerges from the shadows, his presence radiating a dark and oppressive energy. The ground trembles beneath his massive body as he prepares to confront you.",
        "The battle with Rykard is a grueling test of endurance and strategy. His immense size and dark powers create a chaotic and challenging fight. Melina's guidance helps you navigate through the tumultuous battle, allowing you to find the moments to strike effectively."
    );

    static final List<String> AFTER_SERPENT = List.of(
        "After a fierce and prolonged struggle, you finally defeat Rykard, his form collapsing into a seething mass of dark energy. The lair falls silent, the oppressive atmosphere lifting slightly as you catch your breath.",
        "Melina approaches, her expression a mix of grim satisfaction and concern. 'Rykard's defeat is a significant victory,' she says. 'But the final challenges lie ahead. Stay strong and focused for what is to come.'"
    );

    static final List<String> BEFORE_MOHG = List.of(
        "The path now leads you to the subterranean lair of Mohg, Lord of Blood. The air is thick with the scent of blood and corruption, and the walls are lined with dark, pulsating veins. The environment is both grotesque and foreboding.",
        "Melina's presence is a reassuring guide as you navigate through the labyrinthine tunnels. 'Mohg's blood magic is a powerful and dangerous force,' she warns. 'This battle will test every ounce of your strength and resolve.'",
        "As you reach the heart of Mohg's lair, the Lord of Blood emerges from the shadows, his form cloaked in a dark aura of blood magic. His presence is both terrifying and commanding, a testament to his mastery of his dark powers.",
        "Dearest Miquella. You must abide alone a while. Welcome, honored guest. To the birthplace of our dynasty!"
    );

    static final List<String> AFTER_MOHG = List.of(
        "The battle with Mohg is a grueling and intense fight. His blood magic and relentless attacks push you to your limits, requiring you to use every ounce of your strength and skill to survive. Melina's guidance helps you navigate through the chaos and find the openings to strike.",
        "After a taxing and prolonged battle, you manage to defeat Mohg, his form dissolving into a cloud of dark, crimson mist. The lair falls silent, the oppressive atmosphere lifting slightly as you catch your breath.",
        "Melina approaches, her expression one of quiet resolve. 'Mohg's defeat is a testament to your strength and determination,' she says. 'But the final challenge lies ahead - the domain of Morgott, the Omen King.'"
    );

    static final List<String> BEFORE_MORGOTT = List.of(
        "With the Fire Giant defeated, you now stand on the precipice of a new challenge. The path ahead leads to Morgott, the Omen King. This battle will push you to the edge.",
        "The landscape transforms as you approach Morgott's domain. The air is thick with dark energy, and the atmosphere feels heavy with the weight of impending conflict.",
        "Melina stands by your side, her presence a steadying force. 'Morgott is a formidable foe,' she says. 'His power and dark magic will test everything you have learned.'",
        "As you enter the arena, Morgott emerges from the shadows, his form imposing and malevolent. His dark energy crackles with every movement, signaling the fierce battle to come.",
        "Graceless Tarnished. What is thy business with these thrones? Ahh... Godrick the Golden. The twin prodigies, Miquella and Malenia. General Radahn. Praetor Rykard. Lunar Princess Ranni. Wilful traitors, all. Thy kind are all of a piece. Pillagers. Emboldened by the flame of ambition. Have it writ upon thy meagre grave: Felled by King Morgott! Last of all kings."
    );

    static final List<String> AFTER_MORGOTT = List.of(
        "The clash with Morgott is fierce and relentless. His attacks are swift and devastating, requiring you to use every ounce of skill and strategy to survive.",
        "Melina's guidance proves invaluable as you navigate through Morgott's dark magic and brutal strikes. Each moment of the battle is a test of your endurance and resolve.",
        "After a grueling fight, Morgott falls, his dark form dissipating into the shadows. The arena falls silent, and a sense of grim satisfaction settles over you.",
        "Melina approaches, her expression a mix of relief and concern. 'Morgott's defeat is significant,' she says. 'But the path ahead is even more daunting. The Fire Giant awaits us.'"
    );

    static final List<String> BEFORE_FIRE_GIANT = List.of(
        "The path now leads to the fiery domain of the Fire Giant. The landscape is a nightmarish vision of molten rock and burning ash. The heat is almost unbearable.",
        "Melina's presence remains a beacon of hope as you traverse the treacherous terrain. 'The Fire Giant is a colossal foe,' she warns. 'This battle will push you to your limits.'",
        "As you approach the arena, the ground shakes violently. The Fire Giant emerges from the flames, his immense form radiating intense heat and power.",
        "The battle with the Fire Giant is a test of endurance and strategy. His attacks are powerful, and the landscape itself seems to fight against you. Melina's guidance helps you find the right moments to strike and dodge his blows."
    );

    static final List<String> AFTER_FIRE_GIANT = List.of(
        "The battle is a relentless struggle, with the Fire Giant's attacks shaking the very ground beneath you. Each moment is a fight for survival.",
        "Molten rock and fiery eruptions add to the chaos of the battle. Every strike and dodge requires careful timing and strategy. Melina's presence is a source of strength and support.",
        "After an exhausting fight, the Fire Giant falls, his massive form collapsing into a smoldering heap. The heat begins to dissipate as you catch your breath.",
        "Melina approaches, her expression a mix of relief and concern. 'The Fire Giant's defeat is a monumental achievement,' she says. 'But our journey is nearing its end. The final challenge awaits us at the base of the Erdtree.'"
    );

    static final List<String> ERDTREE_DECISION = List.of(
        "With the Fire Giant defeated, you and Melina stand before the colossal Erdtree. Its branches reach high into the sky, a symbol of both life and power.",
        "Melina looks at you, her eyes filled with a mixture of determination and sadness. 'We have come so far,' she says, her voice trembling slightly. 'To burn down the Erdtree, a great sacrifice must be made.'",
        "The weight of her words is almost unbearable. The thought of losing Melina, who has become so integral to your journey, is heart-wrenching.",
        "Her presence has been a constant source of strength and guidance. Her selflessness and unwavering resolve are both inspiring and heartbreaking.",
        "As you prepare for the final confrontation, Melina's demeanor is calm but tinged with sorrow. 'This is the path we must take,' she says. 'But know that your strength and resolve have been the true driving force behind our journey.'",
        "The moment of decision arrives. Melina stands before the Erdtree, ready to make the ultimate sacrifice. The air is thick with tension and emotion.",
        "Melina's eyes meet yours, filled with a mixture of determination and acceptance. 'If you choose to take my place,' she says softly, 'know that it will change everything.'",
        "The choice is heart-wrenching. Letting Melina sacrifice herself is painful, but it is the only way to achieve your goal. Alternatively, taking her place means facing the consequences of becoming a Lord of Chaos.",
        "Melina's resolve is unwavering. 'If you choose to let me proceed,' she says, 'I will accept my fate with honor. Your journey will continue, and the world will be forever changed.'",
        "You are left with a choice that will define the future. The decision to let Melina sacrifice herself or to take her place will shape the outcome of your journey and the fate of the world."
    );

    static final List<String> ENDING_LET_MELINA = List.of(
        "If you chose to let Melina sacrifice herself: Melina's resolve is unwavering as she steps forward, ready to make the ultimate sacrifice. 'Thank you for standing by me until the end,' she says, her voice trembling with emotion.",
        "As Melina initiates the sacrifice, the Erdtree is engulfed in a blinding inferno. Her form merges with the flames, and the world changes as the balance of power shifts.",
        "The forces of chaos are unleashed, and you remain, bearing witness to the new reality that emerges from the ashes. Though Melina is gone, her sacrifice has paved the way for a new era.",
        "The journey has reached its end. The world has been irrevocably changed, and you must now navigate this new reality shaped by your choices and the sacrifices made.",
        "The path forward is uncertain, but your role - whether as a Lord of Chaos or as a witness to Melina's sacrifice - will define the future of this world."
    );

    static final List<String> ENDING_TAKE_HER_PLACE = List.of(
        "As you step forward, Melina's expression changes to one of profound relief and sadness. 'You have chosen a path of great consequence,' she says, her voice filled with emotion.",
        "Melina steps back, allowing you to prepare for the ultimate sacrifice. A surge of power and chaos envelops you as the Erdtree is consumed by raw, untamed energy.",
        "The world shifts and changes, the balance of power is altered. You find yourself transformed into a Lord of Chaos, living but forever changed. The world around you is a new, chaotic reality.",
        "The sacrifice was immense, and the consequences are profound. The forces of chaos are unleashed, and you must navigate this new reality with the weight of your choice bearing heavily on you."
    );

    static final List<String> DECISION_RETRY = List.of(
        "There is no other way.. you must decide");

    static final List<String> BEFORE_BEAST_CLERGYMAN = List.of(
        "With the Erdtree's destruction, the path leads you to a new and foreboding challenge. The Beast Clergyman awaits. His power is vast and his purpose shrouded in mystery.",
        "The landscape transforms once again. Dark clouds loom overhead, and a sense of impending doom fills the air. The Beast Clergyman's domain is a place of shadows and ancient power.",
        "Without Melina by your side, the weight of your journey feels even heavier. The trials you have faced have been immense, and the challenges ahead are formidable.",
        "As you approach the arena, he emerges from the shadows. His form is both majestic and terrifying, a blend of beastly and divine. His presence is both a challenge and a test of your resolve.",
        "Thou, who approacheth Destined Death. I will not have it stolen from me again.",
        "The battle with the Clergyman is fierce and unrelenting. His attacks are swift and devastating, each move a blend of arcane power and physical might.",
        "The fight is grueling, requiring every ounce of your strength and strategy. The Clergyman's form shifts and changes, making each phase of the battle unique and challenging."
    );

    static final List<String> AFTER_BEAST_CLERGYMAN = List.of(
        "As the battle progresses, Maliketh falls. His immense power is subdued, but the victory comes at a cost. The weight of your journey presses heavily upon you as you prepare for the next challenge.");

    static final List<String> BEFORE_GIDEON = List.of(
        "With Maliketh defeated, you now face Sir Gideon Ofnir, the All-Knowing. His knowledge of the world's secrets is vast, and his power is formidable.",
        "The arena for this battle is a grand, ancient hall, filled with relics and symbols of bygone eras. The air is thick with knowledge and power.",
        "The absence of Melina is a poignant reminder of the sacrifices made and the trials yet to come. Sir Gideon's challenge will test not only your strength but also your resolve and understanding of the world.",
        "As Sir Gideon appears, his gaze is piercing and filled with ancient wisdom. He stands as a guardian of the knowledge that will determine the future of the world.",
        "Ahh, I knew you'd come. To stand before the Elden Ring. To become Elden Lord. What a sad state of affairs. I commend your spirit, but alas, none shall take the throne. Queen Marika has high hopes for us. That we continue to struggle. Unto eternity.",
        "The battle with Sir Gideon is a test of both intellect and combat prowess. His attacks are strategic, and his knowledge of the world's secrets makes him a formidable adversary."
    );

    static final List<String> AFTER_GIDEON = List.of(
        "The fight is intense, requiring you to anticipate his moves and counter his strategies. Sir Gideon's power is vast, and each moment of the battle is a test of your skills and understanding.",
        "After a prolonged and challenging fight, Sir Gideon falls. His form dissipates, leaving behind the remnants of his vast knowledge and power."
    );

    static final List<String> BEFORE_GODFREY = List.of(
        "With Sir Gideon defeated, you now face Godfrey, the First Elden Lord. This battle is a culmination of your journey's trials.",
        "The arena is a grand, ancient battlefield, echoing with the memories of past glories and epic conflicts. The atmosphere is charged with the power of the ancient Elden Lords.",
        "Without Melina's guidance, the weight of this challenge feels even more immense. Godfrey's legacy and power are legendary, and this battle will be a true test of your worth.",
        "As Godfrey appears, his presence is awe-inspiring and commanding. His form radiates power, and his strength as a warrior is unparalleled. Prepare for the ultimate test of your abilities.",
        "It's been a long while, Morgott. Long and hard didst thou fight. Tarnished Warrior. Spurned by the grace of gold. Be assured, the Elden Ring resteth close at hand. Alas, I am returned. To be granted audience once more. Upon my name as Godfrey, The first Elden Lord!",
        "The battle with Godfrey is a test of raw strength and combat skill. His attacks are powerful and relentless, requiring you to use every ounce of your strength and strategy.",
        "The fight is fierce and unyielding, with Godfrey's prowess as a warrior pushing you to your limits. Each strike and maneuver must be executed with precision and determination."
    );

    static final List<String> AFTER_GODFREY = List.of(
        "After a grueling and intense battle, Godfrey falls, his form dissipating into the annals of history. The victory is hard-won, and the path ahead remains uncertain.");

    static final List<String> BEFORE_MALENIA = List.of(
        "With Godfrey defeated, the final challenge awaits. Malenia, Blade of Miquella, and Malenia, Goddess of Rot, are the ultimate trials of your journey.",
        "The arena is a nightmarish vision of decay and rot. The air is thick with the stench of corruption and the remnants of Malenia's power.",
        "The absence of Melina is keenly felt. Malenia's challenge will test every aspect of your strength and resolve, and the stakes are higher than ever.",
        "As Malenia emerges, her form is both beautiful and terrifying, a manifestation of rot and divine power. Prepare for the final confrontation that will shape the world's fate.",
        "I dreamt for so long. My flesh was dull gold...and my blood, rotted. Corpse after corpse, left in my wake... As I awaited... his return. ... Heed my words. I am Malenia. Blade of Miquella. And I have never known defeat.",
        "The battle with Malenia is a test of endurance and skill. Her attacks are swift and devastating, and her power as the Goddess of Rot is formidable."
    );

    static final List<String> AFTER_MALENIA = List.of(
        "The fight is intense, with Malenia's corruption spreading and affecting the battlefield. Each moment requires careful strategy and precise execution to overcome her power.",
        "After a harrowing and challenging fight, Malenia falls, her form dissolving into the rotting landscape. The victory is bittersweet, and the path to the final confrontation remains clear."
    );

    static final List<String> BEFORE_RADAGON = List.of(
        "With Malenia defeated, you now face the ultimate challenge: Radagon of the Golden Order. This final battle will determine the fate of the world.",
        "The arena is a cosmic expanse, filled with the remnants of divine power and celestial energy. The air is charged with the force of the Elden Beast's power.",
        "The absence of Melina is a stark reminder of the sacrifices made and the choices that have led to this moment. The final confrontation will test everything you have fought for.",
        "As Radagon emerges, his form are majestic and terrifying, a culmination of divine and cosmic power. Prepare for the ultimate battle that will decide the world's fate.",
        "The final battle is a monumental clash of divine and cosmic forces. Radagon's power and the Elden Beast's energy create a formidable challenge."
    );

    static final List<String> AFTER_RADAGON = List.of(
        "The fight is epic and relentless, with every attack and maneuver requiring the utmost precision and strategy. The fate of the world rests on this battle.",
        "After a titanic struggle, Radagon and the Elden Beast fall. The cosmic energy dissipates, and the world begins to settle into a new reality. The journey's end is both victorious and tragic."
    );

    static final List<String> EPILOGUE_ELDEN_LORD = List.of(
        "If Melina was sacrificed: You stand victorious but alone. The title of Elden Lord is yours, but the absence of Melina casts a shadow over your triumph. The world is now yours to shape, but the cost of victory is profound.",
        "The paths are now set, and the world's future is shaped by the choices made. Whether as Elden Lord or Lord of Chaos, the journey's end is a testament to the trials and sacrifices that have defined your path."
    );

    static final List<String> EPILOGUE_CHAOS = List.of(
        "The world is consumed by death and fire, and Melina remains, transformed by the chaos. She approaches you, her form a blend of sorrow and rage.",
        "In this world of devastation, Melina's eyes burn with a promise of vengeance. 'You have brought ruin to all,' she says, her voice filled with anguish. 'The one who walks alongside flame, Shall one day meet the road of Destined Death. Good-bye.'"
    );

}
