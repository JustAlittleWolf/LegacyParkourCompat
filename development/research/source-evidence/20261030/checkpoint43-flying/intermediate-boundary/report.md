# Intermediate boundary: flying local-player ladder suppression

## Verdict

The local-player suppression change is introduced in **1.16.0**. It is present in every checked patch release 1.16.1–1.16.5, with no later change to the override in those sources. The 1.15.2 inherited behavior is shift-only; in 1.16.0 onward the local player additionally requires not flying. Therefore the source-supported release boundary is 1.15.2 → 1.16.0, not an unknown point within 1.16.0–1.16.5.

## Release and source identity checks

All compared sources are in the requested local Mojmap roots. For 1.16.0, 1.16.1, 1.16.2 and 1.16.5, I checked each `mojmap.ready.json` release ID/mapping, verified the declared SHA-256 of `mojmap.sources.sha256`, and confirmed the relevant `LocalPlayer.java` file hash matches that manifest entry. Values:

| Release(s) | LocalPlayer SHA-256 | Provenance detail |
|---|---|---|
| 1.15.2 | `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689cbd` | Audit endpoint sidecar; Mojmap |
| 1.16.0–1.16.1 | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` | 1.16.0 and 1.16.1 ready/provenance pins and source manifests |
| 1.16.2–1.16.5 | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` | 1.16.2 and 1.16.5 ready/provenance pins and source manifests; for 1.16.3/1.16.4, the in-root `mojmap/.decompile-provenance.json` hashes each source file and pins that release’s Mojang client, `client.txt` mappings, and version metadata. The `LocalPlayer.java` entries match the actual file hashes. |

The endpoint audit’s 1.15.2 and 1.16.5 file hashes also match its corrected `source-hashes-v2.tsv`. The 1.16.0–1.16.5 source trees contain no Minecraft data/resource tag files; the 1.16.3 and 1.16.4 trees also contain their per-source provenance JSON. I did not infer tag membership.

## Exact behavior and gates

- **1.15.2:** `LocalPlayer` has no `isSuppressingSlidingDownLadder` override. Inherited `LivingEntity.isSuppressingSlidingDownLadder()` returns `isShiftKeyDown()` (`LivingEntity.java:2686–2688`).
- **1.16.0–1.16.5:** `LocalPlayer.isSuppressingSlidingDownLadder()` returns `!abilities.flying && super.isSuppressingSlidingDownLadder()` (1.16.0/1.16.1 `LocalPlayer.java:347–349`; 1.16.2–1.16.5 `:348–350`). The inherited predicate remains `isShiftKeyDown()` in every checked release. With shift held, flying makes the old local player predicate true and the new one false.
- The movement predicate is reached only after the entity passes the climbability gate and the negative-Y suppression conditions. In 1.15.2 `onLadder()` rejects spectators and accepts its explicit ladder/vine/scaffolding cases or its usable-trapdoor fallback (`LivingEntity.java:1226–1248`). In 1.16.x `onClimbable()` rejects spectators, accepts `block.is(BlockTags.CLIMBABLE)`, **or** accepts the explicit usable-trapdoor fallback (`LivingEntity.java:1361–1392` in 1.16.0/1.16.1; `:1363–1394` in 1.16.2–1.16.5). No `CLIMBABLE` membership assumption is needed for the fallback witness.
- The common fallback witness is a nonspectator LocalPlayer whose feet state is an open trapdoor above a ladder, with matching trapdoor/ladder facing. Both versions check that fallback directly; 1.15.2 compares the lower block with `== Blocks.LADDER`, whereas 1.16.x uses `BlockState.is(Blocks.LADDER)`.
- After climbability succeeds, `handleOnClimbable` first sets `g = max(y, -0.15)`. It sets `g` to zero only if `g < 0`, the feet block is not scaffolding, the suppression predicate returns true, and `this instanceof Player` (1.15.2 `LivingEntity.java:1989–2000`; 1.16.x `:2087–2100` in 1.16.0/1.16.1 and `:2089–2102` in 1.16.2–1.16.5). The local player satisfies the player-instance gate. Thus with the common trapdoor witness, shift held, flying, negative vertical motion, and no overriding collision, the old branch zeros negative Y while the new branch leaves it at no less than -0.15.

The candidate’s additional witness limitations still apply: movement is conditional on ordinary dry land travel, no fall-flying or passenger branch, and an unclipped downward move. The source comparison does not prove map prevalence or a particular tag’s contents. No tag resource is available in these roots, so tag-based examples remain unverified; the explicit trapdoor path closes the candidate’s needed block-state dependency.

## Inputs consulted

- `.native-source/20261030/1152-1165-pool33/flying-ladder-independent-audit/audit.md` and its corrected hash sidecar.
- Corrected endpoint identity/inventory and ordered movement map in `.native-source/20261030/1152-1165-pool33/`.
- Read-only exact Mojmap roots `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.15.2` and `1.16`, `1.16.1`, `1.16.2`, `1.16.3`, `1.16.4`, `1.16.5`.
