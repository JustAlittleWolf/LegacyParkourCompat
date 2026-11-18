# 1.10 → 1.10.1: ability-flight branch and live glide flag

## Evidence identity

Read-only canonical sources: decompiled_minecraft/<version>/ornithe-feather/.

| Release | Feather mapping artifact | Artifact SHA-256 | Provenance manifest SHA-256 |
|---|---|---|---|
| 1.10 | feather-gen2:1.10+build.2 | ad7cf6744d81d0260c9d97ddfe8250b2fdbd54fe332d4d00266b3545d8ddf8dd | df266e2c3009dd1a873c1665c30fe388078ed1c6227668e10cde87409a18c6da |
| 1.10.1 | feather-gen2:1.10.1+build.2 | 6e8ba44832b7b77d913962462bde55084005d5de6389232453afc0bc0339ea37 | a9a50497930848bf0ddec732b6d424e947f643a8fa6290e01425ac2bbfce219f |

Mapping metadata source SHA-256: 1.10 a1eb1ccff25e2b76fa1d08e9b515508e97ac4837fbcacbd7f77b3a01026d3950; 1.10.1 8648e97ee398b11151cb2d441161629abde561afed9c933c18fbc622e2c9904a. The manifests also pin each raw client artifact and exact release metadata. This report records those local manifest identities; no source generation was run.

## Slice and result

**No difference found for the bounded operation.** The PlayerEntity.moveRelative(float,float) override is the same in 1.10 (PlayerEntity.java:1390–1409) and 1.10.1 (PlayerEntity.java:1390–1409). The enclosing movement math and gate order checked in LivingEntity.moveRelative(float,float) also match (LivingEntity.java:1332–1385 in both releases). LivingEntity.java is byte-identical across the pair (SHA-256 d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82). PlayerEntity.java file SHA-256 is db7bbbc15e2bcb0de18476fec1b88e73eccf8ae63b170276b1d8327dd0be796b in 1.10 and 020370cffde2d1fe9f1b63fa5d51f1af2fab095908b19e79f88a7bcac7dfd501 in 1.10.1; the relevant method body is unchanged despite other file differences. LocalClientPlayerEntity.java is byte-identical in both (SHA-256 a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443).

For a LocalClientPlayerEntity, tick() reaches its superclass tick when its current chunk is loaded (1.10 LocalClientPlayerEntity.java:150–155); it inherits PlayerEntity.tick() (PlayerEntity.java:174–205) and the living movement phase. In that phase, the "travel" section calls flyingTick() and then virtual moveRelative(...) (LivingEntity.java:1742–1748). The local-player override of isLocallyControlled() returns true (LocalClientPlayerEntity.java:457–460), satisfying the parent movement gate (LivingEntity.java:1332–1336). These exact files and relevant method bytes are unchanged in 1.10.1.

In the inherited player override, the branch condition is exactly abilities.flying && !isRiding(); it does not test creative mode. It calls super.moveRelative(...) first, then restores the captured vertical velocity scaled by 0.6, restores speedInAir, and directly calls setFlag(7, false) (PlayerEntity.java:1391–1406). With flag 7 true on entry, the parent call enters its isFallFlying() branch and performs the glide movement, including move(...) (LivingEntity.java:1336–1384); the player override clears flag 7 only after that parent call returns. Thus a non-riding local player with ordinary creative abilities.flying and an already-live flag 7 can execute one glide movement in this travel phase before the player override clears that flag. This is a one-invocation, initial-state claim, not a claim about any later reassertion of the bit.

## Writer ownership and bounds

isFallFlying() reads flag 7 (LivingEntity.java:2088–2089). The shared flag accessor reads and updates bit 1 << index in syncedData (Entity.java:1704–1715). Grepping all direct setFlag(7, ...) writes in each release yields the same source owners:

- PlayerEntity.moveRelative: unconditional false write under the ability-flight/non-riding branch, after super.moveRelative.
- LivingEntity.moveRelative: false on ground only under onGround && !world.isClient in the glide branch (LivingEntity.java:1382–1384).
- LivingEntity.flyingTick: writes its locally computed flag only under !world.isClient (LivingEntity.java:1754–1772).
- Additionally, ServerPlayerEntity.setFlying writes true and clearFlying writes true then false (ServerPlayerEntity.java:1042–1049); this server-side owner is not a client travel step and is the same in both releases.

Bound to passive local movement with flag 7 true at entry and the ordinary ability-flight flag true. I did not establish which external event supplied or most recently synchronized the starting bit, whether it remains true across ticks, or what happens after a later reassertion. No behavior claim is made for riding, a logical server, altered/nonstandard synced data, timing or latency, or other writer effects. No unrelated fall-distance, cap, starting/restart, persistence, or version-cutover claim is included. No client/network launch or execution was performed.


