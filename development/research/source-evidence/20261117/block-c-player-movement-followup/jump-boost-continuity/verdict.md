# Independent source continuity audit

**Terminal status: COMPLETED**

## Bound input

- Comparison: `jump-boost-continuity-source/comparison.md`
- SHA-256: `febcb13a90d3e4a78e579ca8d6356c069c00dca5ac56d1845872869fb936dc14` (matched before review)
- Scope: exact canonical local `decompiled_minecraft/<version>/mojmap` source and its adjacent readiness, provenance, and source-hash manifests only.

## Verdict

The comparison's narrow continuity claim is supported for the nine named releases: 1.17.1, 1.18, 1.18.1, 1.18.2, 1.19, 1.19.1, 1.19.2, 1.19.3, and 1.19.4. Each ready marker and provenance file names the expected exact version and `mojmap`; each ready marker's source-manifest SHA-256 matches the adjacent manifest, and the `LivingEntity.java` content hash matches its manifest entry. The examined supporting `Entity.java`, `Player.java`, and `BlockBehaviour.java` files also match their manifest entries in all nine trees.

In every `LivingEntity.java`, `getJumpPower()` returns `0.42F * getBlockJumpFactor()` as a `float`. `getJumpBoostPower()` is declared `double`, but its enabled term computes `0.1F * (amplifier + 1)` with float multiplication before widening; `hasEffect(MobEffects.JUMP)` is its effect gate, and the no-effect branch is `0.0`. `jumpFromGround()` adds the float base to the double-returning boost term, stores the double sum as Y velocity, and retains X/Z. The nine examined method bodies have these SHA-256 identities:

| Release | `LivingEntity.java` SHA-256 |
|---|---|
| 1.17.1 | `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f` |
| 1.18 | `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` |
| 1.18.1 | `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` |
| 1.18.2 | `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782` |
| 1.19 | `00c591dd2cac49ee5ccad8705f7dd879ad5d29f0360b98742166294989b3531c` |
| 1.19.1 | `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77` |
| 1.19.2 | `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77` |
| 1.19.3 | `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db` |
| 1.19.4 | `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165` |

The `LivingEntity` jump call gate is equivalent in each release: the call is reached for grounded jumping or the water-jump threshold case, with `noJumpDelay == 0`; lava goes through `jumpInLiquid`. This is a separate call-site gate from the Jump Boost effect check inside `getJumpBoostPower()`. `Player.jumpFromGround()` delegates to the superclass in each release. The default block jump-factor property is `1.0F`, and the examined factor lookup uses the below-block factor when the current factor equals `1.0F`.

## Limits

This verdict establishes continuity only across those exact nine releases and the directly examined jump calculation, effect condition, caller gate, player delegation, and default block-factor path. It does not establish when the expression originated before 1.17.1, behavior in unlisted versions, every non-default block factor path, or tick-by-tick runtime parity. The source supports only the narrow observation that no armor/equipment attribute participates in these examined expressions; it does not support a broader claim about attribute behavior elsewhere.
