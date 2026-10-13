# F003: 1.10.2 adds obstacle-triggered local-player jump input

- Older version A: 1.9.4, Ornithe Feather `net.ornithemc:feather-gen2:1.9.4+build.2`
- Newer version B: 1.10.2, Ornithe Feather `net.ornithemc:feather-gen2:1.10.2+build.2`
- Mechanic / coverage slice IDs: local-player auto-jump; `LOCAL-AUTO-JUMP-INPUT`
- Classification: added mechanic
- Confidence: source-confirmed
- Applicability: B local camera player with auto-jump enabled (default true), grounded, not sneaking or riding, nonzero movement input, and the helper's obstacle/landing geometry checks satisfied
- First changed release: 1.10.2 within this paired comparison; releases outside the pair were not assessed
- Runtime validation: not performed

## Paired evidence

- A artifact: source manifest SHA-256 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`; original artifact-manifest SHA-256 `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`. `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, full-file SHA-256 `8AAF711948B7602C2E6C015A37E36ED06073D39727D999D80480B4910B704F5D`: `serverTickAi` lines 586-597 and `mobTick` lines 604-760 contain no auto-jump timer/input write; the class has no `move(double,double,double)` override or `autoJump` method. Search of the A client-entity source tree found no `autoJump` or `ticksToNextAutojump` symbol. The local player inherits the ordinary movement/jump path.
- B artifact: source manifest SHA-256 `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`; original artifact-manifest SHA-256 `6b402f3e6d6cf2f7b3647806364ff44214c47348fefd6949fad03e2011379116`. `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, full-file SHA-256 `A9637065F21AD67464EB5C204C74EBF228C3BB0DA8A96DDF4AE73C0490FED443`: auto-jump defaults enabled at line 105 and reads `GameOptions.autoJump` at line 228; `serverTickAi` lines 602-613; `mobTick` lines 620-783; `move(double,double,double)` lines 812-817 calls `super.move` then `autoJump`; `autoJump(float,float)` lines 823-923 checks the state and collision geometry and sets `ticksToNextAutojump=1` at line 914. The timer consumer in `mobTick` lines 672-677 sets `input.jumping=true`; `bl4` prevents that synthetic input from toggling creative flight at lines 719-727.
- Paired jump consumer: A `net/minecraft/entity/living/LivingEntity.java` full-file SHA-256 `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`, `mobTick` lines 1645-1716; B same path, SHA-256 `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82`, `mobTick` lines 1681-1752. Both call `serverTickAi` before testing `jumping`; if grounded and jump cooldown is zero, that path invokes `jump()`. `PlayerEntity.jump()` is at A lines 1361-1369, SHA-256 `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85`, and B lines 1380-1388, SHA-256 `a055b84b98d98e828e177cad9bba47a1334ee9bf3ecd4331792979106235a302`; the paired method bodies are equal.
- B input producer: `net/minecraft/client/Minecraft.java`, SHA-256 `0979d770e8742a07bb8c54cf7fd3449a8c43a4f48910600ffabf75f1f67ba2d9`, assigns `KeyboardInput` to the local player at lines 1913 and 1946. `KeyboardInput.tick()` reads movement/jump keys; full source SHA-256 `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a`.
- Movement caller: A `LivingEntity.moveRelative(float,float)` lines 1302-1470, SHA-256 `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`; B lines 1332-1506, SHA-256 `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82`. Its movement branches call `this.move(...)`, which dispatches to the B local-player override for the local camera player.
- Revised mapped artifacts used for bytecode confirmation: revision `feather-r1-2026-10-07`; A immutable jar SHA-256 `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`; B immutable jar SHA-256 `0c1d71990c9c0d7cc88debf7e67663bd64c8dc7de5872176088a3119527fb28b`. `javap -c -p` on these exact jars confirms B `move` invokes `autoJump`, B `autoJump` writes the one-tick timer, B `mobTick` consumes it and writes `Input.jumping`, and A's local-player class has none of those fields or methods. Relevant diagnostics contain no LocalClientPlayerEntity or autoJump repair entries. Independent operations audit passed; the source trees and raw inputs match their original manifests, but the unavailable original derived jars are not proven byte-identical to these revised jars.

## Source-level difference

B detects eligible collision geometry after the local player's movement call and schedules a one-tick auto-jump. In the next local-player `mobTick`, the timer is consumed after input ticking and sets `input.jumping=true`. The call to `super.mobTick()` then copies that input into `LivingEntity.jumping` and processes the jump in the same invocation when the ordinary grounded/cooldown guards pass. A has no corresponding local-player timer, movement override, helper, or synthesized jump input. B also suppresses treating this generated input as the creative-flight double-tap.

## Reachability and dependencies

The local player receives `KeyboardInput`; its normal movement branches reach virtual `Entity.move` through `LivingEntity.moveRelative`. B's local-player override calls the superclass movement first and runs the helper on the actual horizontal displacement. The helper requires the option and player-state guards, nonzero movement, and collision/landing checks. Its timer writer, timer consumer, serverTickAi copy, and LivingEntity jump consumer form a source- and bytecode-confirmed path. The finding does not claim an exact trajectory or enumerate every geometry case.

## Consequence and uncertainty

When the helper's guards and geometry checks pass, B can initiate a player jump without a physical jump-key press; A has no corresponding path. This establishes the paired-version behavior difference. Exact resulting position and follow-on movement depend on the movement and collision scenario and are not claimed. Source/revision identity remains limited by the unavailable original derived jars.

## Handoff

Review this exact paired finding independently before implementation. Keep the broader source report active; the remaining local-player tick differences, collision/shape coverage, decompiler diagnostics outside these methods, and all other planned inventories remain open.
