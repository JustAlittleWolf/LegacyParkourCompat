# Independent jump draft review

Review scope: exact 1.20.4 Mojmap to 1.20.5 Mojmap; 1.20.6 consulted only as later context. This is a review of two immutable draft revisions, not pair-freeze or full-pair coverage acceptance. No implementation, wiki, product history, tests, builds, launches, or decompilation were used.

## Immutable inputs

- `jump-strength-attribute-and-execution-gate.md` SHA-256: `a0197fa7e0392296d34b33d7f4845840937ea8dc2cef0a0a6abc2c5624a60377`
- `grounded-flight-toggle-jump.md` SHA-256: `84adc37aceb8b180a90100ac5e4c01c282f346f41ff5fc4f04410b2799023c1a`

## Source identity and integrity

The compared sources are the exact published source trees under `decompiled_minecraft/<version>/mojmap`, with matching ready records and source manifests. Mapping identity is Mojmap (official Mojang client mappings); decompiler metadata identifies Vineflower and Java 25. Every class cited below was SHA-256 checked against the corresponding `mojmap.sources.sha256` entry (one manifest match each). The 1.20.6 classes consulted have hashes equal to the corresponding 1.20.5 classes and are context only.

| Version | Source manifest SHA-256 | Provenance SHA-256 |
|---|---|---|
| 1.20.4 | `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` | `10914d9b7b3fc29de066ca24f7d905e4d4f0a451a6c81729a2d1981addfc1385` |
| 1.20.5 | `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484` | `1d9b247fb839ad0e234054cba1668073245e60d86783eca40d44c2b7b23c6242` |
| 1.20.6 context | `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311` | `ee53e67e7ab51069a0eaae5a0ce13d49a9f972bdc13e3c31e041afa77f4d26e8` |

Class hashes verified against those manifests:

| Class | 1.20.4 SHA-256 | 1.20.5 SHA-256 | 1.20.6 context SHA-256 |
|---|---|---|---|
| `net/minecraft/client/player/KeyboardInput.java` | `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0` | `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0` | `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0` |
| `net/minecraft/client/player/LocalPlayer.java` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` | `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa` | `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa` |
| `net/minecraft/world/entity/LivingEntity.java` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` |
| `net/minecraft/world/entity/player/Player.java` | `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` | `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` | `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` |
| `net/minecraft/world/entity/ai/attributes/Attributes.java` | `287dcd477ccca2aebd24ad28db7b0e7a1e9dc9bbeb272d330cd15504b8669ff7` | `7e30d87c7a58b14d0052d2f9f7319d997b49ae7d025579cd762d28e845b2e82e` | `7e30d87c7a58b14d0052d2f9f7319d997b49ae7d025579cd762d28e845b2e82e` |

## Draft 1: Jump-strength attribute and execution gate

**Independent verdict: needs correction (claim is source-supported; player attribute applicability needs a paired citation).**

The core delta is correct. For equal logical player state, block jump factor, Jump Boost state/amplifier, sprint state, yaw, and old/new default strength of `0.42F`, 1.20.4 computes `0.42F * blockFactor + boost` and always writes the result to vertical movement; sprint uses a float `0.2F` product before the vector addition, and `hasImpulse` is then set. In 1.20.5, `getJumpPower()` delegates to `getJumpPower(1.0F)`, whose ordered expression is `(float)getAttributeValue(JUMP_STRENGTH) * scale * blockFactor + boost`. The generic attribute has default `0.42F`, is appended to living attributes, and the jump executor does nothing to movement or `hasImpulse` when power is `<= 1.0E-5F`. Otherwise it writes vertical power; while sprinting it adds the horizontal vector using a double `0.2` product, then sets `hasImpulse`. The Jump Boost expression remains `0.1F * (amplifier + 1.0F)`.

Player reachability is ordinary: `Player.jumpFromGround()` calls `super.jumpFromGround()`, and `Player.createAttributes()` builds on `LivingEntity.createLivingAttributes()`. The 1.20.5 living builder includes `JUMP_STRENGTH`; the 1.20.4 living builder does not. In 1.20.4 `Attributes.JUMP_STRENGTH` is the horse-specific `horse.jump_strength` with default `0.7`, while player jump power remains hard-coded. The draft should cite `Player.createAttributes()` for both versions (1.20.4 lines 204–210; 1.20.5 lines 221–230) to close the player-specific attribute dependency and can cite 1.20.4 `Attributes.java` lines 24–26 to clarify the former horse-only registration. The stated consequence about player attribute modification is otherwise supported.

Paired movement and attribute refs:

- 1.20.4 `LivingEntity.java`: `getJumpPower`/`getJumpBoostPower`/`jumpFromGround`, lines 2000–2017; `createLivingAttributes`, lines 280–288. `Player.java`: `createAttributes`, lines 204–210; `jumpFromGround`, lines 1440–1448. `Attributes.java`: `JUMP_STRENGTH`, lines 24–26.
- 1.20.5 `LivingEntity.java`: `createLivingAttributes`, lines 292–305; `getJumpPower` overloads, `getJumpBoostPower`, and `jumpFromGround`, lines 2057–2081. `Player.java`: `createAttributes`, lines 221–230; `jumpFromGround`, lines 1462–1470. `Attributes.java`: generic `JUMP_STRENGTH`, lines 45–47.
- Input/reachability: `KeyboardInput.java` lines 22–30 sets `jumping` from `options.keyJump.isDown()` in both versions. `LocalPlayer.java` lines 655–676 (1.20.4) / 657–679 (1.20.5) consumes the same jump input in `aiStep`; the ordinary grounded jump is dispatched through the inherited player jump path. 1.20.6 context has these same cited class hashes as 1.20.5.

Concrete precondition and consequence: on an ordinary grounded player jump, same block factor/effect/sprint/yaw state, a non-default 1.20.5 jump-strength attribute can change the computed vertical impulse. At computed power `<= 1.0E-5F`, the 1.20.5 executor preserves the old vertical velocity and does not mark an impulse, whereas 1.20.4 writes even a zero/negative computed value and marks an impulse. A sprinting successful jump also has the float-vs-double horizontal-product precision difference. The actual later trajectory is an inference from these source-level velocity writes and remains untested.

## Draft 2: Grounded flight-toggle jump

**Independent verdict: needs correction (trigger is correct; movement consequence is too unconditional).**

The `LocalPlayer.aiStep()` change is correctly identified. With the same raw input and state, ordinary keyboard input sets `input.jumping`; after the first rising edge initializes `jumpTriggerTime` to 7, a later rising edge while it is still nonzero, when `mayfly` is true, always-flying mode is false, auto-jump is not forcing the input, and the player is not swimming, toggles flight. 1.20.4 then updates abilities and clears the trigger timer. 1.20.5 first checks whether the toggle turned flight on and `onGround()` is true; only on that branch does it call `jumpFromGround()`. It then updates abilities and clears the timer. Turning flight off and enabling flight while airborne do not make that call. `Player.aiStep()` decrements a positive `jumpTriggerTime` in both versions (1.20.4 line 508; 1.20.5 line 517), so the cited “before it expires” condition is accurate.

The draft overstates the movement result as a guaranteed velocity and sprint impulse. In 1.20.5 the delegated `LivingEntity.jumpFromGround()` only writes vertical movement, adds sprint movement, and sets `hasImpulse` if computed power is strictly above `1.0E-5F`; even then, horizontal sprint impulse is present only if the player is sprinting. Revise the consequence to say that the grounded flight-enable edge invokes the Player ground-jump method, which applies movement iff the computed power passes that gate, with an additional horizontal impulse iff sprinting. The Player override also performs non-movement bookkeeping after `super`; food/exhaustion and stats are outside the requested scope.

Paired trigger/call refs:

- 1.20.4 `LocalPlayer.java`, `aiStep`, input capture lines 655–676 and mayfly/toggle branch lines 717–734; `Player.java`, timer decrement lines 507–509 and `jumpFromGround`, lines 1440–1448; `LivingEntity.java`, `jumpFromGround`, lines 2008–2017.
- 1.20.5 `LocalPlayer.java`, `aiStep`, input capture lines 657–679 and mayfly/toggle/call branch lines 720–741; `Player.java`, timer decrement lines 516–518 and `jumpFromGround`, lines 1462–1470; `LivingEntity.java`, gated `jumpFromGround`, lines 2069–2081.
- Ordinary input route: in both versions `KeyboardInput.java` lines 22–30 reads the configured jump key into `jumping`; `LocalPlayer.aiStep()` consumes that state for the rising-edge test. 1.20.6 context has identical hashes for the cited source classes as 1.20.5.

Concrete precondition and consequence: a LocalPlayer with `mayfly`, not in always-flying mode or swimming, standing on ground, and whose second rising jump input reaches the nonzero timer branch enters flight and immediately invokes `Player.jumpFromGround()` in 1.20.5. Under computed jump power `> 1.0E-5F`, this changes vertical movement; if sprinting, it also changes horizontal movement. The corresponding 1.20.4 branch has no direct ground-jump call. This immediate velocity consequence is source-derived; later position/tick trajectory is an inference and was not runtime-tested.

## Review boundary

These verdicts bind only to the two draft hashes listed above and the exact source identities listed here. This bounded independent review does not claim complete source inventory, closed pair dependencies, full-pair coverage, or pair freeze.
