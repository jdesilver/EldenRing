# Elden Ring — A Text RPG

A fan-made, text-based retelling of *Elden Ring*: create a character, choose your
weapons, and fight a gauntlet of bosses in time-based combat. It runs as a free
website (no download) **and** as the original Java console program.

## ▶ Play now (free, in your browser)

**https://jdesilver.github.io/EldenRing/**

Nothing to install — it works on desktop and mobile, with a keyboard, mouse, touch,
or screen reader.

> If the link 404s, the site just needs to be switched on once — see
> [Publishing the website](#publishing-the-website) below.

## How to play

- **Menus are numbered.** Type the number of your choice and press <kbd>Enter</kbd>,
  or select one of the on-screen buttons.
- **Story screens wait for you.** Press <kbd>Enter</kbd> (or select **Continue**) to
  advance.
- **Combat is turn-based.** Each boss attack charges up while you **Attack**, **Dodge**,
  **Heal**, or **Wait**. Dodge in time *and* in a correct direction to avoid the hit;
  otherwise the blow lands when the timer runs out. While the boss recovers, it's
  vulnerable — keep hitting.
- **You can't break it.** Any input that isn't a valid option simply returns you to the
  same prompt.

## Features

- **Time-based combat** with light, heavy, and Focus-fuelled special attacks.
- **16 bosses** across two phases each, with unique attack combos and dialogue.
- **Character building**: pick from six tiers of weapons, allocate eight stats, and
  upgrade your weapon at the Sites of Grace between fights.
- **A branching ending** decided at the Erdtree.
- **Accessible by design**: semantic HTML, an ARIA live region so screen readers
  announce each screen, full keyboard control, large touch targets, and support for
  reduced-motion and high-contrast preferences.

## Accessibility

The whole game is text and numbered choices, which makes it naturally screen-reader
friendly. The web version adds:

- a live region that reads out new narration and menus as they appear;
- on-screen buttons for every menu option (and **Continue** / **Yes** / **No**), so you
  never have to type if you'd rather click or tap;
- visible keyboard focus, a "skip to input" link, and 44px-minimum touch targets;
- honoring `prefers-reduced-motion` and `prefers-contrast`.

## Run the website locally

The site is plain HTML/CSS/JavaScript in [`docs/`](docs) — no build step. You can just
open `docs/index.html` in a browser, or serve the folder:

```bash
# Option A: Node (no install thanks to npx)
npx serve docs

# Option B: Python 3
python -m http.server -d docs 8000
```

Then visit the URL it prints (e.g. `http://localhost:8000`).

### Tests

A small headless test suite fuzzes the game for crashes and verifies the combat
win path:

```bash
node docs/test.js
```

## Run the original Java console version

The original terminal game lives in the repository root.

1. **Install Java (JDK 17 or newer).** Check what you have:
   ```bash
   java -version
   ```
   If it's below 17, [install a current JDK](https://adoptium.net/). Make sure `java`
   and `javac` report the same version — if `java -version` shows 1.8 while `javac` is
   newer, put your JDK's `bin` directory first on your `PATH`.
2. **Compile:**
   ```bash
   javac *.java
   ```
3. **Run:**
   ```bash
   java Main
   ```

## Project layout

```
.
├── docs/            # the web version (served by GitHub Pages)
│   ├── index.html
│   ├── styles.css
│   ├── data.js      # bosses, weapons, and narration (data only)
│   ├── game.js      # game engine + browser front-end
│   └── test.js      # headless tests
├── Main.java        # the Java console game (script/flow)
├── Combat.java      # time-based combat engine
├── Bestiary.java    # all bosses, as data
├── Armory.java      # weapon tiers
├── Story.java       # all narration
└── ...              # Player, Weapon, Boss, Console, enums, records
```

The web version is a faithful port of the Java game — the same prompts, narration, and
combat math (including its quirks).

## Publishing the website

The site is served by **GitHub Pages** from the `docs/` folder. To turn it on (one time):

1. Push this repository to GitHub.
2. Go to **Settings → Pages**.
3. Under **Build and deployment**, set **Source** to *Deploy from a branch*, choose the
   `main` branch and the `/docs` folder, and **Save**.
4. After a minute, the game is live at `https://<your-username>.github.io/EldenRing/`.

## Credits

Fan project by James DeSilver. Not affiliated with FromSoftware or Bandai Namco;
all *Elden Ring* names and lore belong to their respective owners.
