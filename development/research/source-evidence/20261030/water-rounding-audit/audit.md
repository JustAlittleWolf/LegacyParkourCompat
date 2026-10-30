# Independent source audit: water movement efficiency rounding

## Reviewed revision

Draft: `development/research/source-evidence/20261029/spindown/frontier/1206-1211-local-tick/water-movement-efficiency-rounding.md`

Draft SHA-256: `df8623cb8cfec515a4c372a99a5056f4a82d662686e81105d63f9fe6f7bdbc66`.

This is a bounded source-only review of the draft and the adjacent source coverage report. It does not freeze the pair or establish runtime parity.

## Verdicts

**Accept the core operation difference, with a narrowed movement consequence.** In `1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java`, `travel(Vec3)` lines 2117–2129 obtains the Depth Strider level as float, caps it at 3, halves it off ground, then evaluates `($$speed - 0.02F) * $$level / 3.0F` for acceleration. In `1.21.0` and `1.21.1`, the same method instead casts `getAttributeValue(WATER_MOVEMENT_EFFICIENCY)` to float, halves that when airborne, and multiplies the speed difference directly by it. The water gates and downstream order through `moveRelative`, `move`, and the subsequent horizontal slowdown are unchanged for this slice.

**Accept the built-in Depth Strider II value claim.** In `1.21.0` and `1.21.1`, `Enchantments.java` lines 346–365 registers Depth Strider with an additive water-efficiency attribute effect using `LevelBasedValue.perLevel(0.33333334F)`. `LevelBasedValue.perLevel(float)` uses that value as both base and per-level increment, and `Linear.calculate(level)` is `base + perLevelAboveFirst * (level - 1)`; level II therefore calculates to `0.6666667F`. `Attributes.java` lines 101–103 declares the water-efficiency attribute with default `0.0`, range `[0.0, 1.0]`, and `setSyncable(true)`. `LivingEntity.createLivingAttributes()` includes it. The local calculation consumes the attribute value present on the client entity; equipment modifier calculation/state synchronization is a dependency for the enchantment-backed witness. Treat the witness as conditional on the received value being the built-in corresponding value.

**Accept equal-default behavior.** At no modifier / default state, old Depth Strider level `0` and new attribute default `0.0` both leave the initialized acceleration at `0.02F`; the positive-factor branch is skipped. The rounding witness compares corresponding states (old level II; new received attribute value `0.6666667F`), not arbitrary mismatched client equipment/attribute snapshots. The draft correctly notes that the value is a received-state input; that gate should remain explicit.

**Accept the stated acceleration numbers.** Using Java float rounding at each operation, a grounded, non-sprinting Player with speed `0.1F`, Depth Strider II / received attribute `0.6666667F`, and nonzero horizontal input gets `0.07333333045244217F` in the old method and `0.07333333790302277F` in the new method. The changed acceleration is passed to `Entity.moveRelative`, which converts it to an input vector and adds it to delta movement. This proves a reachable local movement input difference. Avoid claiming every trajectory or all components change by exactly one ULP; the direct result established here is the acceleration (one float ULP for this witness) supplied to the vector conversion.

The adjacent water-slowdown expression uses the same changed factor/order, but for the specific grounded Depth Strider II witness with the default `getWaterSlowDown() == 0.8F`, both float evaluations produce `0.6306667327880859F`. Do not add a distinct consequence for that slowdown from this witness. Other factor/speed combinations are not characterized here.

## Boundary and provenance

The exact `1.21.0` (`1.21`) Mojmap tree is available. Its `LivingEntity.java` SHA-256 is `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8`, identical to 1.21.1; its `Enchantments.java`, `Attributes.java`, `LevelBasedValue.java`, `Entity.java`, `Player.java`, and `LocalPlayer.java` are also byte-identical to 1.21.1. Thus the new formula and built-in effect are present in 1.21.0, and no change in these methods occurs between 1.21.0 and 1.21.1. The source tree has `.decompile-provenance.json` (SHA-256 `90246f277aeffd2350e42e17245faa51928a949c8db4427f3be04a732527deb7c`), whose inputs identify the 1.21 client artifact and 1.21 metadata and whose embedded source map contains the verified hash. Unlike 1.20.6 and 1.21.1, the 1.21.0 directory has no `mojmap.ready.json` or external source manifest; retain that qualification if a formal publication/ready identity is required.

The endpoint ready records identify exact Mojmap releases and Vineflower decompilation; the endpoint source manifests contain the selected `LivingEntity.java` entries matching these SHA-256 values. 1.20.6 ready/provenance identifies the client SHA-256 `02dfd345ac1ad55692d5dbc8486ac7e4fea72cd54ac494a79cd48963048e56b2`; 1.21.1 identifies `499f6897d1837516680f3114072d8106e11c9adcd933fe5cf051b551089b0c99`. Their manifest and ready identities are preserved in the neighboring source coverage report.

The exact introduction interval is therefore **after 1.20.6 and no later than 1.21.0**. The supplied material does not inspect every intermediate release, so it does not establish the earliest patch release within that interval.

## Reachability and dependencies

`LocalPlayer.isEffectiveAi()` returns `true` in both endpoints, enabling the local-controlled `LivingEntity.travel` branch. `Player.travel()` reaches the inherited travel implementation for the stated ordinary grounded, non-swimming, non-passenger path; its relevant pre-adjustment does not bar the setup. `Player.getSpeed()` reads the movement-speed attribute, and `Player.createAttributes()` supplies default `0.1F`. `Entity.moveRelative()` calls `getInputVector(input, acceleration, yaw)` and adds the resulting vector to delta movement. A nonzero movement input above the input-vector epsilon is sufficient; zero input is not.

The adjacent `coverage.md` explicitly leaves client attribute packet/application timing, collision and shape providers, packaged resources/tags, fluid sample providers, portal transfer, other packet responses, and remaining modifier/effect consumers open. Those are outside this single arithmetic finding. The attribute-state dependency remains open for cases where client-side received state is not the built-in corresponding modifier value; it is closed only for the conditional arithmetic witness as stated above.

## Review scope

Inspected the assigned draft, its neighboring source coverage report, endpoint source identities and selected source bodies, exact 1.21.0 source provenance and matching intermediate source files, and movement call-path methods named above. No product implementation, wiki, backlog, or repository history was consulted. No tests, builds, gameplay, source generation, or product edits were performed.
