# Final author review — powder-snow frost speed math

## Frozen inputs

The reviewed comparison is exactly `source/comparison-r1.md`, SHA-256 `8cdf3f5abd9727853d045e089159b933315887d67eb32b878f9450b56e32d172`. The independent blind audit `blind-audit/frozen.md` is unchanged at SHA-256 `c8a9ec154aaf09943f5fe20c50d1ce853dc1dae2fafc7b624a5c1e7fea79bea2`.

## Verdict

**Accepted for the bounded source claim.** At the five specified anchors, the frost additive, attribute calculation buckets, effect/sprint operands, and Player double-to-float speed read match for the stated arithmetic. The numeric witnesses match source arithmetic at the displayed precision, conditional on the stated common modifier snapshot and distinct per-version modifier IDs. This is not a runtime parity verdict.

The exact LocalPlayer reader chain is supported at every anchor: `LocalPlayer` extends `AbstractClientPlayer`, which extends `Player`; LocalPlayer has no `getSpeed()` override. `LocalPlayer` directly calls `getSpeed()` in its auto-jump check, while the inherited `LivingEntity` movement path passes `getFrictionInfluencedSpeed(...)` to `moveRelative(...)`; its friction-speed method calls `this.getSpeed()`. That virtual call on a LocalPlayer resolves to `Player.getSpeed()`, which returns `(float)getAttributeValue(Attributes.MOVEMENT_SPEED)`. The Player `aiStep()` float assignment into the inherited `speed` field is present as reported, but it is not the LocalPlayer movement getter: the Player override reads the current attribute directly.

| Anchor | LocalPlayer → Player source path | LocalPlayer / AbstractClientPlayer SHA-256; both match the source manifest or provenance hash map |
| --- | --- | --- |
| 1.20.5 Mojmap | `LocalPlayer.java:90,894`; `AbstractClientPlayer.java:18`; `LivingEntity.java:2270,2314-2319`; `Player.java:1511-1513` | `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa` / `759f8f03b89dab1f6b65c73e1e25a68ae6a6083cb49b5d2719136edda7edf696` |
| 1.21 Mojmap | `LocalPlayer.java:92,897`; `AbstractClientPlayer.java:18`; `LivingEntity.java:2253,2297-2302`; `Player.java:1494-1496` | `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583` / `759f8f03b89dab1f6b65c73e1e25a68ae6a6083cb49b5d2719136edda7edf696` |
| 1.21.2 Mojmap | `LocalPlayer.java:92,897`; `AbstractClientPlayer.java:17`; `LivingEntity.java:2352,2396-2401`; `Player.java:1489-1491` | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` / `fa15cd69238b46b410bc709c8ae0a629d5f3f817a6bc454e70a1d60bf7aa785c` |
| 1.21.4 Mojmap | `LocalPlayer.java:93,927`; `AbstractClientPlayer.java:17`; `LivingEntity.java:2363,2407-2412`; `Player.java:1492-1494` | `145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611` / `fa15cd69238b46b410bc709c8ae0a629d5f3f817a6bc454e70a1d60bf7aa785c` |
| 26.2 unobfuscated | `LocalPlayer.java:108,1010`; `AbstractClientPlayer.java:19`; `LivingEntity.java:2677,2720-2729`; `Player.java:1446-1448` | `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6` / `7825a8d4e8e24928cba18f68109c91479b78fb14fcd8b81f0e045b8d02a559cc` |

The operation *bucket* sequence is source-stable: `ADD_VALUE`, then `ADD_MULTIPLIED_BASE`, then `ADD_MULTIPLIED_TOTAL`, then sanitization. The source does **not** promise a stable order among modifiers within a bucket. Each snapshot stores a bucket's modifiers in `Object2ObjectOpenHashMap` and iterates its `.values()`. For the stated Speed I, Slowness I, and sprint factors, all six possible total-factor orders were checked: they can vary in the final double's low bits, but each order rounds to the same reported final float. The combined raw double in the comparison is therefore a representative finite-double result, not a portable ordered-value claim.

The witness modifiers have distinct identifiers within every anchor: in 1.20.5, frost/sprint/Speed/Slowness use UUIDs `1eaf83ff-7207-4596-b37a-d7a07b3ec4ce`, `662A6B8D-DA3E-4C1C-8813-96EA6097278D`, `91AEAA56-376B-4498-935B-2F7F68070635`, and `7107DE5E-7CE8-4030-940E-514C1F160890`; from 1.21 onward they use distinct default-namespace identifiers for `powder_snow`, `sprinting`, `effect.speed`, and `effect.slowness`. Thus the table is valid as an arithmetic witness if those modifiers coexist on one movement-speed instance. It does not assert a particular producer, effect delivery, or schedule.

## Residual limits

The five anchors do not establish every intermediate version boundary. No source evidence here establishes powder-snow contact/frost production timing, packet arrival, or runtime parity. The author comparison correctly bounds those claims and distinguishes the Player attribute getter from its cached speed-field assignment.