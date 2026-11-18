# Product integration receipt: local mouse-turn pitch setter

## Product, evidence, and review identities

- Product baseline: `aae9c86464638cb5ed7faa535c92569b89484906`.
- Product code commit/tree: `9ec57a6256b39ea8a290b298322d827d8b3002de` / `d2d74eedc026224e8d01bdfc30e01c5ec7bae252`.
- Source evidence archive commit/tree: `89bfa2d2a27ee67a969935b3e17101b344f34b67` / `99be874c0f954055e38b6e1b141f7ebd6574a384`; bundle SHA-256 `95bbd3e86c34ccb719e6b8aae5d7b4277e9343f3d96490a803cc7209ff0e6349`; 18 accepted files.
- The evidence subtree was compared against the archive commit by Git blob identity; all 18 files matched. No candidate or evidence archive history was included in product ancestry. The 1.20.5→1.21.2 adjacent records and separate 1.21.1→26.2 destination witness remain bound by the archive README and report/verdict files.
- Sanitized candidate: `7efa25a99be34579fe14855cfc0c475f0c2f4595` / `b5fad51b13a356eee5cd2860b4cdd27322dc8708`; its history is not in product ancestry.
- Independent product review: `c_raw_pitch_turn_product_review`, signed PASS for the exact product code commit and tree. The review confirmed both setter calls in `Entity.turn` bytecode, the ordinal-0 target, source artifact bytes, scope and resolver routing.
- Ready ref: `refs/campaign-ready/c-raw-pitch-turn-1211`.

## Integrated operation and scope

The operation is only the first pitch setter argument in ordinary `Entity.turn(double,double)`. The client-only `EntityMixin` modifies `Entity.setXRot(float)` ordinal 0 in that method, and only for `LocalPlayer`; it dispatches through `MovementRuntime.find`. The exact 26.2 bytecode computes the argument as `getXRot() + ((float) deltaY * 0.15F)`. Its second setter call is the subsequent `[-90F,90F]` clamp and remains untouched. Setter calls from teleports, packets, other callers, and remote entities are outside the hook.

The `V1_20_2` native identity change is the lower resolver fence. `V1_21` clamps only finite values below `-90F` or above `90F`; non-finite values and in-range floats, including signed zero, pass through unchanged. This restores the accepted 1.20.5–1.21.1 first-setter behavior before the 26.2 setter's modulo-and-clamp operation.

| Selected profile groups | Resolved provider | Result |
|---|---|---|
| `V1_8`, `V1_8_1`, `V1_8_5`, `V1_8_9`, `V1_9`, `V1_9_3`, `V1_10`, `V1_10_1`, `V1_11`, `V1_11_1`, `V1_11_2`, `V1_12`, `V1_13`, `V1_13_1`, `V1_14`, `V1_14_1`, `V1_14_2`, `V1_15`, `V1_15_2`, `V1_16`, `V1_16_2`, `V1_17`, `V1_17_1`, `V1_18`, `V1_18_2`, `V1_19`, `V1_19_3`, `V1_19_4`, `V1_20`, `V1_20_2` | `V1_20_2` native fence | Native argument; no historical claim before 1.20.5 |
| `V1_20_5`, `V1_21` | `V1_21` enabled provider | Historical finite first-setter clamp for selected 1.20.5–1.21.1 profiles |
| `V1_21_2`, `V1_21_4`, `V1_21_5`, `V1_21_6`, `V1_21_9`, `V1_21_10`, `V1_21_11`, `V26_1`, `CURRENT` | No applicable provider | Native fallback; no later historical claim |

The Change comment cites Mojmap 1.21.1 `Entity.turn`/`Entity.setXRot` and 26.2 unobfuscated `Entity.turn`/`Entity.setXRot`, with exact source lines. The source archive binds the 1.20.5→1.20.6, 1.20.6→1.21, and 1.21→1.21.1 continuity records and the 1.21.1→1.21.2 setter cutoff. No backlog association was made because this batch did not establish a matching existing finding. No backlog or primary checkout was modified.

## Build disposition

Exact Windows command on product code commit `9ec57a6256b39ea8a290b298322d827d8b3002de` and tree `d2d74eedc026224e8d01bdfc30e01c5ec7bae252`:

```text
.\gradlew.bat build -x test --init-script development/disable-tests.gradle --console=plain
```

Result: `BUILD SUCCESSFUL` in 11 seconds; 19 actionable tasks, 3 executed and 16 up-to-date. The init script disables all Gradle `Test` tasks and `-x test` excludes the test task. No tests, game, TAS, Gym, server, Docker or runtime launch was performed. Compilation does not establish runtime parity.
