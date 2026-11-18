# Powder frost speed and synchronization review, revision 1

## Bound inputs

This revision is bound to the exact authorized documents:

- Author comparison: `late/source/comparison.md`, raw SHA-256 `3affe7a67d8e98065135a68dfe5b3eb33e75cc1f2296ef56735329bbec2956f1`.
- Blind freeze: `late/blind-audit/frozen.md`, raw SHA-256 `2eeaa398bfd19354f5fbd2bb0ad1c4e571f2ed2038f36b06ebb42cf3155a4822`.
- Prior verdict: `late/blind-audit/verdict.md`, raw SHA-256 `53142418c5d760928e4dfc938348c8086e714c673222891f604f43f10effb4e7`.

The five Mojmap source roots and their client/mapping provenance remain those identified in the frozen artifact. The additional `Player.java`, `LocalPlayer.java`, and `AbstractClientPlayer.java` source files below were read from those roots and checked against their published per-file source hashes. The 1.21.5 packet, attribute, and entity files were checked against its source manifest.

| Version | `Player.java` SHA-256 | `LocalPlayer.java` SHA-256 | `AbstractClientPlayer.java` SHA-256 |
|---|---|---|---|
| 1.20.5 | `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` | `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa` | `759f8f03b89dab1f6b65c73e1e25a68ae6a6083cb49b5d2719136edda7edf696` |
| 1.21 | `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71` | `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583` | `759f8f03b89dab1f6b65c73e1e25a68ae6a6083cb49b5d2719136edda7edf696` |
| 1.21.2 | `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de` | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` | `fa15cd69238b46b410bc709c8ae0a629d5f3f817a6bc454e70a1d60bf7aa785c` |
| 1.21.4 | `c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1` | `145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611` | `fa15cd69238b46b410bc709c8ae0a629d5f3f817a6bc454e70a1d60bf7aa785c` |
| 1.21.5 | `8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036` | `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef` | `971bc296fe1feeb3fc54a2b78c0a0a6d0df2578fb79428c451f25be57c20a648` |

Additional 1.21.5 sources read: `ClientPacketListener.java` SHA-256 `284f34159a1baf30b6b540751ad2fa62344c179d5ed6a7bbcb5c888665779617`; `ClientboundUpdateAttributesPacket.java` `a62a937338270df1ce0ec2b195fdbefcc5bc35b3db31bfcdd868baa1e27c1fdf`; `ClientboundSetEntityDataPacket.java` `bb588c05c9d664feeec1f665c925bb8ae7c661ea2ee868a568c83f96b1ecee3d`; `Attributes.java` `14bcc5821d0f67f41fdc3e512556181823cff5c43f84fc1007e51d25f413a3ea`; and `Entity.java` `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4`.

## Corrected caller anchors

The earlier ranges were broader navigation spans. These are the exact call sites in `net/minecraft/world/entity/LivingEntity.java`; each `travel` precedes the listed frost calls:

| Version | `travel(...)` | `removeFrost()` | `tryAddFrost()` | Caller side gate |
|---|---:|---:|---:|---|
| 1.20.5 | 2683 | 2697 | 2698 | Helpers unconditional; frozen-tick progression separately client-gated |
| 1.21 | 2678 | 2692 | 2693 | Helpers unconditional; frozen-tick progression separately client-gated |
| 1.21.2 | 2771 | 2790 | 2791 | Helpers unconditional; frozen-tick progression separately client-gated |
| 1.21.4 | 2782 | 2801 | 2802 | Helpers unconditional; frozen-tick progression separately client-gated |
| 1.21.5 | 2766 | 2784 | 2785 | Both helpers inside `level() instanceof ServerLevel` |

The caller behavior frozen earlier is unchanged; the call-site rows replace the nonmatching range endpoints in the earlier documents.

## Actual local player speed dispatch

In all five versions, `LocalPlayer` extends `AbstractClientPlayer`, and `AbstractClientPlayer` extends `Player`. `LocalPlayer` and `AbstractClientPlayer` do not declare `getSpeed()`. `Player` overrides it to return `(float)this.getAttributeValue(Attributes.MOVEMENT_SPEED)`:

| Version | `LocalPlayer` extends | `AbstractClientPlayer` extends | `Player.getSpeed()` | `Player.aiStep()` cache write after `super.aiStep()` |
|---|---|---|---:|---:|
| 1.20.5 | `AbstractClientPlayer` (`LocalPlayer.java:90`) | `Player` (`AbstractClientPlayer.java:18`) | `Player.java:1511` | `Player.java:533` |
| 1.21 | `AbstractClientPlayer` (`LocalPlayer.java:92`) | `Player` (`AbstractClientPlayer.java:18`) | `Player.java:1494` | `Player.java:535` |
| 1.21.2 | `AbstractClientPlayer` (`LocalPlayer.java:92`) | `Player` (`AbstractClientPlayer.java:17`) | `Player.java:1489` | `Player.java:556` |
| 1.21.4 | `AbstractClientPlayer` (`LocalPlayer.java:93`) | `Player` (`AbstractClientPlayer.java:17`) | `Player.java:1492` | `Player.java:559` |
| 1.21.5 | `AbstractClientPlayer` (`LocalPlayer.java:93`) | `Player` (`AbstractClientPlayer.java:17`) | `Player.java:1470` | `Player.java:569` |

For ordinary non-passenger, non-flying travel, `Player.travel` delegates to `super.travel`, which reaches `LivingEntity.travel`. Its grounded movement path calls `getFrictionInfluencedSpeed`, then `moveRelative`; the helper invokes `this.getSpeed()`. Virtual dispatch for a `LocalPlayer` therefore reaches `Player.getSpeed()` and reads the current movement-speed attribute directly. `LocalPlayer.aiStep()` calls `super.aiStep()` (1.20.5/1.21/1.21.2/1.21.4/1.21.5 at lines 807/804/795/809/830); `Player.aiStep()` invokes `super.aiStep()` before its `setSpeed(...)` cache write (write lines 533/535/556/559/569). That cache write is not the cause of the speed consumed by the actual player travel read: the overriding `Player.getSpeed()` does not read the `LivingEntity.speed` cache. The `LivingEntity` movement read anchors are `moveRelative(getFrictionInfluencedSpeed(...))` at lines 2270/2253/2352/2363/2395 and helper `getSpeed()` calls at 2314-2315/2297-2298/2396-2397/2407-2409/2438-2439 respectively.

Accordingly, the 1.20.5–1.21.4 post-travel local modifier refresh changes the attribute that a later ordinary travel read obtains directly. The earlier frozen sentence attributing that next read to the post-travel cache refresh is corrected here.

## 1.21.5 client receipt paths

`Attributes.MOVEMENT_SPEED` is marked client-syncable (`Attributes.java:63-65`). A received `ClientboundUpdateAttributesPacket` carries attribute snapshots with a base value and modifier collection; its modifier codec includes modifier ID, amount and operation (`ClientboundUpdateAttributesPacket.java:19-36, 61-78`). The client handler (`ClientPacketListener.java:2179-2199`) resolves the entity and attribute instance, sets the received base, removes its existing modifiers and re-adds snapshot modifiers transiently. Once that handler has applied a snapshot containing the powder-snow modifier, `Player.getSpeed()` reads the updated attribute value; any later ordinary player travel speed read uses it.

Frozen ticks follow a separate metadata path. `Entity.DATA_TICKS_FROZEN` is an integer `SynchedEntityData` accessor (`Entity.java:232, 278, 2425-2430`). `ClientboundSetEntityDataPacket` carries packed `SynchedEntityData.DataValue` entries, and `ClientPacketListener.handleSetEntityData` applies them through `assignValues` (`ClientboundSetEntityDataPacket.java:11-13, 24-42; ClientPacketListener.java:558-564`). This keeps the frozen-tick value available as tracked metadata, but does not itself create the local frost speed modifier.

This proves conditional client-side effect, not when or whether a particular attribute update is emitted or delivered relative to movement. The server send path, packet arrival order, and exact movement tick are unclaimed. The author’s broad “synchronized to clients” statement is only established here as a client handler path for a received update plus a syncable attribute declaration.

## Final verdict

- **Accepted:** the single caller-level movement delta at 1.21.5 (the frost helpers cease running locally); the UUID-to-resource-location identifier representation change, additive transient modifier math and gates; and the direct attribute-to-player-speed path when an update has been applied before the read.
- **Corrected:** the blind freeze’s cache-based explanation. `Player.getSpeed()` overrides the cached base getter in every examined release and reads `MOVEMENT_SPEED` directly. The post-super `setSpeed` assignment is not the actual cause for ordinary player travel.
- **Bounded:** client snapshot application is established; server emission, packet arrival/order and exact tick of movement effect are not.
- **Finding count:** no nested finding. The identifier API representation remains part of the existing frost operation claim; only the 1.21.5 caller gate change is an independently movement-relevant delta in this slice.