# Independent source audit: player-contact coverage r3

## Verdict

**ACCEPT**, bound to `build/later-source-20261116/26.1.2--26.2/player_contact/coverage-r3.md`, SHA-256 `6D38345C0F04E12D1E05C71EAD26D7C54A0C5BE3FED69CD858A1DF994F9C77D0`.

The requested r2 correction is present and source-accurate. The restitution dispatch uses the applicable `Player.canSimulateMovement()` override (`!level().isClientSide() || isLocalPlayer()`), and `LocalPlayer.isLocalPlayer()` returns true. The report separately traces the local-authority path used by collision state/fall handling. The `noPhysics` early return and the report's bounded scope are correctly stated. This accepts only the report's claimed residual slice and restitution source difference; it does not accept/freeze the full pair or establish runtime parity.

## Evidence integrity

- Report SHA-256 recomputed as `6D38345C0F04E12D1E05C71EAD26D7C54A0C5BE3FED69CD858A1DF994F9C77D0`, matching the assignment.
- Provenance file SHA-256 values match the assignment: 26.1.2 `8D727F880CC91F4A297E148C5B75976303D051457334BC689268FBDF631A012A`; 26.2 `A328F5333A1B27AADDE4422B23159A323AE219CCD26F11207C1A5461366F27D1`.
- Both ready markers identify their exact release and `unobfuscated` mapping and have `status=ready`. Recomputed every entry in each ready-bound source manifest: 26.1.2 6,882/6,882 matched, zero mismatches; 26.2 7,055/7,055 matched, zero mismatches. Each manifest-file SHA-256 matches its ready marker.
- Report's compared file hashes match the files: 26.1.2 `Entity.java` `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`; 26.2 `Entity.java` `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`; `LivingEntity.java` `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.

## Independent source trace

- 26.1.2 unobfuscated `Entity.java:704-800`: horizontal collision zeroes the applicable movement components; simulated vertical collision invokes `Block.updateEntityMovementAfterFallOn`. The file has no `restituteMovementAfterCollisions` method.
- 26.2 unobfuscated `Entity.java:711-718` enters the direct-position `noPhysics` path before collision handling. On the collision path, `Entity.java:763-767` updates vertical-collision/on-ground state when vertical movement occurred or the instance is locally authoritative; `Entity.java:777-779` calls `checkFallDamage` only for a locally authoritative instance.
- 26.2 `Entity.java:784-786` dispatches restitution when `canSimulateMovement()` and either moved vertical collision or horizontal collision holds. The exact applied override is `Player.java:1290-1292`, returning `!level().isClientSide() || isLocalPlayer()`. `LocalPlayer.java:378-380` returns true from `isLocalPlayer()`, so a client LocalPlayer passes this gate.
- The authority chain is distinct: `Entity.isLocalInstanceAuthoritative()` at `Entity.java:3589-3595` uses `isLocalClientAuthoritative()` on the client; `Player.java:1280-1283` returns `isLocalPlayer()`; the LocalPlayer override above returns true. Thus the report correctly uses this chain for the no-vertical-movement collision-state gate and fall-damage gate, not as the restitution dispatch implementation.
- The restitution method at `Entity.java:802-856` has the reported entity/block restitution and suppression behavior. `LivingEntity.java:2201-2204` provides the bounciness attribute override, with zero default and 0–1 range in `Attributes.java:31`. The concrete positive-block-bounciness vertical case and nonzero-attribute horizontal case are correctly presented as source preconditions, not measured trajectories.
- The report explicitly limits NO_DIFF to named direct contact/provider/suffocation slices, leaves the collision restitution candidate open, identifies unexamined movement areas, and says the pair remains partial/incomplete/not frozen. I find no expanded claim of full-pair coverage.

No product, backlog, wiki, Git, build, test, runtime, source-generation or canonical-source action was performed.
