# Indigo Game Template

A [Giter8](http://www.foundweekends.org/giter8/) template for [Indigo](https://indigoengine.io/)!

To install, use g8:

```bash
g8 PurpleKingdomGames/indigo.g8
```

or sbt:

```bash
sbt new PurpleKingdomGames/indigo.g8
```

## Supports Mill & sbt

This template supports both Mill and sbt. However, due to Giter8 syntax limitations it defaults to sbt (for historical compatibility).

If you want to use Mill, enter `n` when you see the `use_sbt` prompt.

## Nix Flake included (Optional)

During set up, you'll be asked if you want to include a Nix Flake. This will add a basic nix Flake that provides sbt, Mill (with a JDK aligned to them both), and a node.js base static http-server (usage: `http-server -c-1`), that you can use as a starting point. It also includes a `.envrc` file that automatically loads and unloads the Flake via [direnv](https://direnv.net/) one you add it to Git.

---

Template license
----------------
Written in 2021 by Dave Smith indigo@purplekingdomgames.com

To the extent possible under law, the author(s) have dedicated all copyright and related and neighboring rights to this template to the public domain worldwide.
This template is distributed without any warranty. See <http://creativecommons.org/publicdomain/zero/1.0/>.
