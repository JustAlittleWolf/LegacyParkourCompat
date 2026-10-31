# Independent jump draft review r2

Review scope: exact 1.20.4 Mojmap to 1.20.5 Mojmap, with 1.20.6 consulted only as later context. This report reviews only the revised immutable draft hashes below. It does not assert full-pair coverage or pair freeze. The prior r1 report remains a separate immutable record.

## Immutable inputs

- `jump-strength-attribute-and-execution-gate.md` SHA-256: `b9e0253bde8af1afad34b3d88fd99dca3c4f59d7d2a11131dd9cd482c71b11ad`
- `grounded-flight-toggle-jump.md` SHA-256: `ffe9868aec46f3d3b42b98a2afdfbd3dbcc02c6985eff03eebec32649baee08e`
- Prior r1 report SHA-256 (unchanged): `34dd73d6e346a0ac6b9fe6c208c0763bf5c04a9fea9a375febee35cd5784c388`

## Exact source identity and integrity

Compared inputs: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.20.4\mojmap` and `...\1.20.5\mojmap`, exact release source trees. Mapping is Mojmap (official Mojang client mappings); source metadata identifies Vineflower and Java 25. Every cited class hash below was rechecked against its exact version's `mojmap.sources.sha256` entry; all entries match. 1.20.6 classes were checked only as context and have hashes equal to their 1.20.5 counterparts.

| Version | Source manifest SHA-256 | Provenance SHA-256 |
|---|---|---|
| 1.20.4 | `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` | `10914d9b7b3fc29de066ca24f7d905e4d4f0a451a6c81729a2d1981addfc1385` |
| 1.20.5 | `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484` | `1d9b247fb839ad0e234054cba1668073245e60d86783eca40d44c2b7b23c6242` |
| 1.20.6 context | `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311` | `ee53e67e7ab51069a0eaae5a0ce13d49a9f972bdc13e3c31e041afa77f4d26e8` |

| Cited class | 1.20.4 SHA-256 | 1.20.5 SHA-256 | 1.20.6 context SHA-256 |
|---|---|---|---|
| `net/minecraft/client/player/KeyboardInput.java` | `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0` | `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0` | `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0` |
| `net/minecraft/client/player/LocalPlayer.java` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` | `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa` | `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa` |
| `net/minecraft/world/entity/LivingEntity.java` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` |
| `net/minecraft/world/entity/player/Player.java` | `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` | `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` | `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` |
| `net/minecraft/world/entity/ai/attributes/Attributes.java` | `287dcd477ccca2aebd24ad28db7b0e7a1e9dc9bbeb272d330cd15504b8669ff7` | `7e30d87c7a58b14d0052d2f9f7319d997b49ae7d025579cd762d28e845b2e82e` | `7e30d87c7a58b14d0052d2f9f7319d997b49ae7d025579cd762d28e845b2e82e` |

## Revised draft 1: Jump-strength attribute and execution gate

**Independent verdict: accept.**

The revised paired references close the player-specific attribute dependency. In 1.20.4, `Player.createAttributes()` inherits a living attribute builder with no jump strength, while the registered `JUMP_STRENGTH` is horse-specific (`horse.jump_strength`, default `0.7`). `LivingEntity.getJumpPower()` instead uses hard-coded `0.42F * blockJumpFactor + jumpBoostPower`. Its `jumpFromGround()` always writes vertical power, applies sprint impulse using a float `0.2F` product when sprinting, and sets `hasImpulse`.

In 1.20.5, the Player builder inherits living attributes that now include generic `JUMP_STRENGTH`, default `0.42F`; `getJumpPower()` calls the scale overload with `1.0F`, whose ordered expression is `(float)attributeValue * scale * blockJumpFactor + jumpBoostPower`. `jumpFromGround()` leaves velocity and `hasImpulse` untouched at power `<= 1.0E-5F`; above that gate it writes vertical power, conditionally adds sprint impulse using a double `0.2` product, and sets `hasImpulse`. Jump Boost addition remains `0.1F * (amplifier + 1.0F)`. These statements and the cited line ranges match both source trees.

Paired refs: 1.20.4 `LivingEntity.java` lines 2000–2017 and 280–288; `Player.java` lines 204–210 and 1440–1448; `Attributes.java` lines 24–26. 1.20.5 `LivingEntity.java` lines 2057–2081 and 292–305; `Player.java` lines 221–230 and 1462–1470; `Attributes.java` lines 45–47.

Concrete precondition/consequence under equal player state, block factor, Jump Boost state/amplifier, sprint state and yaw: on a successful ground jump, the 1.20.5 generic player attribute can alter vertical impulse. At or below the threshold it preserves existing vertical velocity and does not set an impulse, where 1.20.4 writes even zero or negative power and marks an impulse. When the power gate passes and sprint is active, horizontal sprint-product precision also differs. Subsequent trajectory effects are source-based inference and were not runtime-tested.

## Revised draft 2: Grounded flight-toggle jump

**Independent verdict: accept.**

The revised input-edge description and movement qualifiers match the source. `KeyboardInput.tick()` sets `jumping` from the configured jump key. In `LocalPlayer.aiStep()`, the previous jump value is captured before input refresh; the branch requires a false-to-true edge and excludes auto-jump forcing (`!$$4` in 1.20.4, `!$$5` in 1.20.5). Under `mayfly` and outside always-flying mode, the first qualifying edge arms `jumpTriggerTime = 7`; while it remains nonzero, a later edge toggles flight if the player is not swimming.

The timer is decremented by `Player.aiStep()`, reached after the `LocalPlayer.aiStep()` branch calls `super.aiStep()`. Thus an initial edge arms 7 and then decrements to 6 after that branch; a later false-to-true edge can take the toggle branch on any of the next six LocalPlayer aiStep calls while the countdown stays nonzero. The revised draft accurately describes the gate as a later rising edge while nonzero; it does not overstate the exact press/release spacing.

On the second edge, 1.20.4 toggles and updates abilities without calling the ground-jump routine. In 1.20.5, the call occurs only if the toggle enabled flight and `onGround()` is true, and it occurs before ability update. The 1.20.5 jump executor writes vertical movement and sets `hasImpulse` only when computed power is `> 1.0E-5F`; it adds the horizontal sprint impulse only when sprinting. Turning flight off or toggling on while airborne does not call the routine. The Player override's stat and food/exhaustion work is outside this review's movement scope.

Paired refs: 1.20.4 `LocalPlayer.java` lines 717–734; `Player.java` timer decrement lines 507–509 and `jumpFromGround()` lines 1440–1448; `LivingEntity.java` `jumpFromGround()` lines 2008–2017. 1.20.5 `LocalPlayer.java` lines 720–741; `Player.java` timer decrement lines 516–518 and `jumpFromGround()` lines 1462–1470; `LivingEntity.java` gated `jumpFromGround()` lines 2069–2081. The unchanged ordinary input source is `KeyboardInput.java` lines 22–30 in both versions.

Concrete precondition/consequence under equal input and player state: a non-swimming, grounded LocalPlayer with `mayfly`, not always-flying, whose later false-to-true jump edge arrives while the timer is nonzero toggles flight. In 1.20.5 this also invokes Player ground jump; if power exceeds the gate, vertical movement changes, and horizontal movement additionally changes if sprinting. The same edge in 1.20.4 has no direct ground-jump invocation. Position/tick trajectory beyond those direct velocity effects is inferred, not runtime-tested.

## Review boundary

These verdicts bind only to the two r2 draft hashes and the source identities above. They do not establish full-pair inventory completeness, dependency closure beyond these findings, pair freeze, implementation, or runtime parity.
