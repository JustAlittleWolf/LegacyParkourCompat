# Source campaign release roster

This is a release-taxonomy inventory for source preparation. It contains no claims about movement behavior. The `1.19` and `1.20` boundaries retain the selected substantive standalone update endpoints; named game drops in `1.21` are represented by their first release plus the latest hotfix selected for comparison. Exact Java release IDs below were checked against Mojang's launcher version manifest on 2026-10-07. Do not extend the roster past native `26.2` or add `1.7.10`.

## Adjacent exact endpoint pairs

`1.8.9 → 1.9.4 → 1.10.2 → 1.11.2 → 1.12.2 → 1.13.2 → 1.14.4 → 1.15.2 → 1.16.5 → 1.17.1 → 1.18.2 → 1.19.2 → 1.19.3 → 1.19.4 → 1.20.1 → 1.20.2 → 1.20.4 → 1.20.6 → 1.21.1 → 1.21.3 → 1.21.4 → 1.21.5 → 1.21.8 → 1.21.10 → 1.21.11 → 26.1.2 → 26.2`

This preserves `1.19.2`, `1.19.3`, and `1.19.4` as distinct boundaries and does not fold the 1.20.2 standalone update into another endpoint. `1.20.4` is the final patch selected for the 1.20.3 Bats and Pots release; `1.20.6` is the final patch selected for Armored Paws 1.20.5. For named 1.21 drops, the selected release/final-patch endpoints are Bundles of Bravery `1.21.2`/`1.21.3`, The Garden Awakens `1.21.4`, Spring to Life `1.21.5`, Chase the Skies `1.21.6`/`1.21.8`, The Copper Age `1.21.9`/`1.21.10`, and Mounts of Mayhem `1.21.11`. Native `26.1.2` and `26.2` complete the requested range.

Supplemental source-only endpoints `1.16.1` and `1.16.2` are available for a specific boundary audit; they are outside the main adjacent roster.

## Taxonomy references

- [Mojang Java version manifest](https://piston-meta.mojang.com/mc/game/version_manifest_v2.json) — exact release IDs and release metadata.
- [Minecraft Wiki: Java Edition version history](https://minecraft.wiki/w/Java_Edition_version_history) — Java release chronology.
- [Minecraft Wiki: Major updates](https://minecraft.wiki/w/Major_updates) — named major-update taxonomy.
- [Minecraft Wiki: Java Edition 1.19](https://minecraft.wiki/w/Java_Edition_1.19) and [Java Edition 1.20](https://minecraft.wiki/w/Java_Edition_1.20) — release-family pages used to distinguish standalone endpoints and named game drops.
- [Minecraft Wiki: Java Edition guides](https://minecraft.wiki/w/Category:Java_Edition_guides) — named update and game-drop guide index, including the 1.21 content drops.

These references are for version taxonomy only. Source-only movement reports must be frozen before any worker reads taxonomy or MCPK wiki audit findings. Movement claims must come from the exact decompiled Minecraft source and bytecode.
