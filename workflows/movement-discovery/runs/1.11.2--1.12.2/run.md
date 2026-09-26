# Discovery: 1.11.2 to 1.12.2

- Status: partial
- Scope: client player movement; older A = 1.11.2; newer B = 1.12.2
- Repository revision and start date: `c133c2999b6673874e35bbdb26759548407f3e11`; 2026-09-26
- Selected naming namespace: Ornithe Feather on both releases, using release-specific artifacts.
- B source command: `gradlew.bat -g .gradle-user-home decompileMinecraft --versions=1.12.2 --mappings=feather`; successful output confirmed requested/resolved 1.12.2, `Applying ornithe feather (1.12.2+build.2)`, `Finished 1.12.2 using ornithe-feather`, and `BUILD SUCCESSFUL`. Raw log not retained; command output is in task transcript.
- A source command: owner reports successful exact 1.11.2 Feather run. Raw log not retained; see cross-task provenance below.
- B toolchain: Gradle 9.7.1; decompiler JVM 25.0.3+9-LTS; target bytecode Java 8. Vineflower/remapper/mapping-io dependency versions remain to be captured.

## Artifact manifest

### A — 1.11.2

- Requested/resolved: 1.11.2 / 1.11.2 (owner's successful log transcript).
- Source root: `decompiled_minecraft/1.11.2/ornithe-feather`.
- Client SHA-256 `be3fff4f2cc005a1310a96389efdeb983d2bcb4b8e747c402acd616ae73d0ba2`; SHA-1 `db5aa600f0b0bf508aaf579509b345c4e34087be`.
- Mapping family/mode: Feather; coordinate `net.ornithemc:feather-gen2:1.11.2+build.2`; Tiny SHA-256 `4fa160c09d83bf61ae21bb74ab1e33b6aabe9b8ec89904b266ad53cecc9c36e6`.
- Remapped jar SHA-256 `19200acf9fdd0395535cc8a880f6ab9f6db427131c6c11259f7a3e1b07845daf`.
- Owner reports remapper invalid-access warnings and fixes for 1 class / 34 members; relevance to movement sources is being checked.

### B — 1.12.2

- Requested/resolved: 1.12.2 / 1.12.2 (successful decompiler output).
- Source root: `decompiled_minecraft/1.12.2/ornithe-feather`; the worktree junction target was verified as `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`.
- Original client URL object/SHA-1: `0f275bc1547d01fa5f56ba34bdc87d981ee12daf`; SHA-256 `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f`.
- Mapping family/mode: Feather; coordinate `net.ornithemc:feather-gen2:1.12.2+build.2`; mapping file hash still to capture.
- Remapped jar `build/minecraft-decompile-cache/1.12.2/client-ornithe-feather.jar`; SHA-256 `d9639e8ba8a34c1f9ad8186b80575bd68440bec29b750b42eb3220cf824429a`.
- Remapper emitted invalid-access warnings and fixed 1 class / 33 members; warning targets must be checked against movement evidence.

### Cross-task provenance

A provenance was relayed by the adjacent-version owner from `C:\Users\Wolfi\.codex\worktrees\movement-discovery-1-11\LegacyParkourCompat\workflows\movement-discovery\runs\1.10.2--1.11.2\run.md`. Do not regenerate 1.11.2. The owner reports exact successful completion in 3m7s, but no raw log or source-hash inventory. This run independently hashes each A source cited.

## Correspondence and call order

- `KeyboardInput.tick()` writes directional booleans, jumping/sneaking, and sideways/forward movement impulses. `LocalClientPlayerEntity.tickMovement()` samples prior flags, invokes input sampling, then handles item slowdown, autojump, sprint and movement dispatch. A/B Feather classes and the local player inheritance chain match in naming; full owner/member correspondence still needs to be recorded.
- In B, the local tick invokes `Tutorial.onPlayerInput(input)` after sampling. `Tutorial` dispatches to `MovementTutorialStep.onPlayerInput`, which only sets the tutorial `moved` boolean; it does not modify player input or movement state.
- Full player -> living -> entity movement call graph and state writers remain open.

## Coverage ledger

- Slice 1.1 / stage 1 / keyboard input sampling and sneak scaling: `compared-no-difference`. Both source paths `net/minecraft/client/entity/living/player/KeyboardInput.java`, lines 1-47. SHA-256 on both sides: `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a`. Directional booleans and impulses, jump/sneak sampling, and `0.3` sneak scaling match textually.
- Slice 1.2 / stage 1 / local player input-to-sprint/autojump integration: `in-progress`. A `LocalClientPlayerEntity.java` source SHA-256 `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed`; B SHA-256 `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc`. The compared region preserves sprint/slowdown/autojump guards; B inserts the tutorial callback. Callback traced to tutorial-only state. Finish method, line and hash audit before closure.
- Slice 3.1 / stage 3 / player relative-acceleration inputs: `compared-no-difference` for the player call path. A `LivingEntity.moveRelative(float sideways,float forwards)` at `LivingEntity.java:1388` and travel call at `1803`; B `moveRelative(float forwards,float sideways,float upwards)` at `LivingEntity.java:1425` and call at `1847`. A LivingEntity SHA-256 `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f`; B LivingEntity `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`; A/B PlayerEntity SHA-256 `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b` / `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e`; A/B Entity `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` / `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`. B adds `verticalSpeed`, and `Entity.updateVelocity` adds it to the normalization and Y component. The local player path supplies `sidewaysSpeed, verticalSpeed, forwardSpeed`; `PlayerEntity.moveRelative` forwards all three arguments. Tree search found the only assignment `MobEntity.java:475`, so player `verticalSpeed` retains default `+0.0F`. The added Y component is zero; B's normalization computes `(sideways² + 0) + forward²`, preserving the 1.11.2 two-input result for finite movement inputs, and horizontal axis expressions retain the same sideways/forward mapping. This disposition is player-only; non-player 3D movement is out of scope. MobEntity writer/class and exact method line closure still need hashing before terminal closure.
- All remaining slices in stages 1-7: `pending`, including player state/gates, living travel and jump, Entity collision/support, blocks/fluids, effects/attributes/equipment, and external/data-driven movement inputs.

## Dependency queue and blockers

- `DEP-A-WARNINGS`: obtain owner's disposition of 1 class / 34 fixed access warnings; ensure cited A movement code is not affected.
- `DEP-B-WARNINGS`: inventory B warning targets and assess movement classes/methods.
- `DEP-MAPPING-HASH`: capture B mapping Tiny SHA-256 if artifact is retained; otherwise record artifact hash limitation.
- `DEP-SOURCE-HASHES`: hash every source file as it is cited, including A files.
- `DEP-DATA`: generated source omits resources; inspect version-matched client jar resources for relevant block/fluid/effect data.

## Finding index

No confirmed movement finding yet. The tutorial input callback was traced and discarded as a trajectory candidate because the movement tutorial handler only updates tutorial progress state.

## Resume checkpoint

- Completed: exact B provenance; aligned Feather family verified; keyboard sampler compared; tutorial callback traced.
- Next: finish stage 1 local tick/player state, then stage 3 travel/jump slices, continuing the documented navigation order.
- Outstanding: warning disposition, B mapping hash, source hashes, and all remaining navigation slices.

## Source audit closure

- Coverage: 2 compared-no-difference; 1 in-progress; remaining slices pending; 0 confirmed findings; 0 stages closed.
- Status: partial. Source hashes/correspondence audit incomplete; raw logs unavailable. A provenance is relayed from its owner, B provenance is independently confirmed. Runtime validation: not performed.
