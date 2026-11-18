# Independent state and predicate audit: 1.21.5 → 1.21.8 Mojmap

## Verdict

**No in-scope mismatch found in the bounded residual state/predicate slices audited below.** The bound source-coverage report's conclusions for sprint/swim/sleep/fall-flight flags, ability defaults, and the sampled inherited movement predicates are supported by these endpoint sources. This verdict does not close full-pair coverage or prove the pair's intermediate boundary.

## Immutable input

- Bound report: `c-queue-1215-1218-state-predicates/report.md`
- Bound report SHA-256: `a00d1e98ff90a954e3a7de225cf01c84aa66c77a0b948509e98f023ffa028b86`
- Exact source sides: `decompiled_minecraft/1.21.5/mojmap` and `decompiled_minecraft/1.21.8/mojmap`.
- Source-manifest SHA-256: 1.21.5 `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9`; 1.21.8 `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008`.
- Local ready/provenance markers identify exact `versionId` and `mojmap`; ready markers report status `ready`, Vineflower, Java 25, with client SHA-256 values `522672ad20b460c02c2e39b6c5035ef6a849af28eb11ab2ac7eb293d395a8c11` (1.21.5) and `ea74e9c5e92d01f95f3d39196ddb734e19a077a58b4c433c34109487d600276f` (1.21.8).
- Audited source-file hashes (SHA-256):
  - `world/entity/Entity.java`: 1.21.5 `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4`; 1.21.8 `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704`.
  - `world/entity/LivingEntity.java`: not separately hashed for this report; exact canonical files inspected.
  - `world/entity/player/Player.java`: 1.21.5 `8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036`; 1.21.8 `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987`.
  - `world/entity/player/Abilities.java`: exact canonical files inspected; no separate file hash recorded.
  - `client/player/LocalPlayer.java`: 1.21.5 `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef`; 1.21.8 `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee`.

## Compared slices and findings

- `Entity.updateSwimming`, shared sprint/swim/shift flag accessors (`isSprinting`/`setSprinting`, `isSwimming`/`setSwimming`), `isUnderWater`, `canSpawnSprintParticle`, and base `canSprint`: same predicates and shared-flag indices/assignments. `updateSwimming` retains the same branch order and gates: current swimming state, sprint flag, water/underwater, passenger exclusion and water fluid-tag test.
- `LivingEntity.isFallFlying` and `isVisuallySwimming`: same shared flag 7 read and same visual-swimming composition. `Player.startFallFlying` still writes shared flag 7 true; `tryToStartFallFlying` still gates on not already flying, `canGlide`, and not in water. `Player.canGlide` retains the flying-ability exclusion. No changed fall-flight state writer or non-item eligibility predicate found in these members.
- `Player.isSwimming`, `updateSwimming`, `canSprint`, `getFlyingSpeed`, `isAffectedByFluids`, and `isPushedByFluid`: same movement gates and values. The player swimming override remains gated by not flying and not spectator before delegating to the shared swimming bit. Sprinting remains permitted for `Player`; flying speed remains `flyingSpeed * 2.0F` while sprinting versus `flyingSpeed`, or `0.025999999F` versus `0.02F` outside free flight.
- `Player.isSleepingLongEnough` remains `isSleeping() && sleepCounter >= 100`. In `Player.tick`, the observed sleep-counter increment/cap and wake reset interval match the bound report's compared behavior; no sleep-eligibility change was found in this bounded check.
- `Abilities`: 1.21.5 fields and NBT save/load defaults compared with 1.21.8 `pack`/`apply` and codec defaults. Values/defaults remain `invulnerable=false`, `flying=false`, `mayfly=false`, `instabuild=false`, `mayBuild=true`, `flyingSpeed=0.05F`, `walkingSpeed=0.1F`; no effective movement default or assignment mismatch found. 1.21.8's representation refactor is not a movement change in the examined mapping.
- `LocalPlayer.canStartSprinting`, `vehicleCanSprint`, and `hasEnoughFoodToSprint`: bodies match. The non-item gates remain forward impulse, enough food/passenger/mayfly, not using an item, no blindness, allowed vehicle, fall-flying/slow-movement conditions, and underwater tests when falling/slow/in water. Item-use state is only noted as the existing predicate gate; item eligibility mechanics are outside this audit.
- Inherited non-item predicates examined at the direct player movement boundary remain unchanged: passenger state, shared shift/crouch bit consumers, and fluid/underwater checks. The earlier bound report's local shift-input transport/API change remains unadjudicated as client/server protocol behavior and is not promoted to an in-scope movement mismatch here.

## Bounds

This is a bounded independent audit, not a full tick-graph audit. It does not close every `LivingEntity`/`Entity` inherited predicate or all predicate callers/writers; it does not trace shift-input packet receiver/default state; and it does not establish an exact introduction point between 1.21.5 and 1.21.8. Item-use/Elytra equipment eligibility, rider synchronization, respawn carry, block/fluid collision behavior, server simulation, and other excluded areas remain outside this verdict. No intermediate patch versions were inspected.

## Terminal

**COMPLETED** — audit of the assigned residual state/predicate queue only. No implementation, tests, runtime, or source generation performed.