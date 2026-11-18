# Independent source audit: 1.15.1 → 1.15.2 local yaw

## Verdict

**No source-confirmed movement difference in the assigned yaw operation.** The ordinary yaw update in `Entity.turn`, and its first dry ground/air nonvehicle contribution through `moveRelative`, have identical math and ordering in exact Mojmap 1.15.1 and 1.15.2. Close this bounded claim as a no-difference result; this verdict is not a full-pair coverage audit.

## Exact source identities and integrity

Both roots are Mojmap, marked `ready`, and identify the requested exact release. The published source manifests were verified against every file in the corresponding local `decompiled_minecraft/<version>/mojmap` tree with zero missing files or hash mismatches.

| Release | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 | Manifest verified |
| --- | --- | --- | --- | --- |
| 1.15.1 Mojmap | `afbb16c3cb832ffee8e4763bf9933d9635ecd811c9fbd7166554b78e59788504` | `6a7af1f5d65d7af165c2ab9a90c1fae20169b38e70c8e92dbc5f59d94efe9461` | `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d` | 3,329/3,329 files; 17,524,592 bytes |
| 1.15.2 Mojmap | `64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0` | `d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e` (locally calculated) | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` | 3,332/3,332 files; 17,543,871 bytes |

The 1.15.1 ready marker publishes the provenance digest, and it matches. The schema-1 1.15.2 ready marker has no `provenanceSha256` field; the digest above is a local file hash, not a marker-bound identity. Its ready marker does bind the source and artifact-manifest digests. This is the provenance-field caveat, not a source-integrity failure. Both artifact-manifest hashes match their ready markers (`d2c40f0770820752fe879670b35bd718b87e4f14491b3ec782e2e74df6b38bbb` for 1.15.1; `208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406` for 1.15.2).

Focused file hashes: `world/entity/Entity.java` is `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` in both releases; `util/Mth.java` is `a842059e498147d368126c896f0036916a90bdd125a09b09f9fe6658f2aaf0a4` in both. `world/entity/LivingEntity.java` differs as a whole (`139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` / `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54`), but its complete `travel(Vec3)` body at lines 1831–1988 and the caller block around lines 2280–2291 compare text-identically.

## Ordinary turn: direct yaw write

In both releases, Mojmap `net/minecraft/world/entity/Entity.java`, `turn(double d, double e)`, lines 326–338, is exactly:

```java
double f = e * 0.15;
double g = d * 0.15;
this.xRot = (float)(this.xRot + f);
this.yRot = (float)(this.yRot + g);
this.xRot = Mth.clamp(this.xRot, -90.0F, 90.0F);
this.xRotO = (float)(this.xRotO + f);
this.yRotO = (float)(this.yRotO + g);
this.xRotO = Mth.clamp(this.xRotO, -90.0F, 90.0F);
if (this.vehicle != null) {
    this.vehicle.onPassengerTurned(this);
}
```

`d`/`e`, `f`/`g`, and their products are `double`; current and previous angles are stored as `float`. Each yaw update widens the stored float for the addition and narrows once at the explicit cast. Current yaw is written before previous yaw. Unlike pitch, yaw receives no clamp, modulo, wrap, or finite check here. The optional passenger callback follows both writes. This sequence is identical in both releases.

The normal local input route is also unchanged: Mojmap `client/MouseHandler.java:202–222` computes the mouse deltas and calls `player.turn(j, k * n)`; this calls the inherited `Entity.turn` for the local player.

## First dry ground/air nonvehicle yaw contribution

`LocalPlayer.aiStep` calls `super.aiStep` (`client/player/LocalPlayer.java:778` in both sources). `LivingEntity.aiStep` then supplies `(xxa, yya, zza)` to `travel` (`world/entity/LivingEntity.java:2282–2287`). In `LivingEntity.travel`, the dry non-fall-flying branch (water/lava gates at 1840–1842) chooses ground friction or the airborne factor at 1888 and calls `moveRelative` at 1889. That call is made before self movement and applies in both on-ground and airborne cases; the passenger branch is not involved for a nonvehicle local player.

Both releases have identical `Entity.moveRelative` and `getInputVector` (`world/entity/Entity.java:1063–1078`): pass `this.yRot` as a `float`; check input squared length against `1.0E-7`; normalize only above length squared 1; scale the input; calculate `float` sine/cosine from `g * (float)(Math.PI / 180.0)`; then rotate X/Z in the same expression order and add the resulting vector to delta movement. `Mth.sin/cos` use the same float lookup-table indexing in both source files (`util/Mth.java:25–30`). The implementation-level yaw contribution therefore has no 1.15.1-to-1.15.2 delta.

## Lawful finite witness

Take a local player with finite `yRot = yRotO = 0`, no vehicle, dry conditions, not fall-flying, and a finite ordinary mouse delta that supplies `turn(16.0, 0.0)`. Both sources produce `g = 2.4` as a `double`, then `yRot = 2.4000000953674316F` and, from the stated previous value, `yRotO = 2.4000000953674316F`. For the subsequent `moveRelative(0.1F, (0, 0, 1))` arithmetic, the sine lookup index is 436 and cosine lookup index is 16820; the added X/Z components are approximately `-0.00417888059` and `0.09991265088`. All inputs and outputs are finite. This gives a reachable nonzero dry movement contribution, and the same witness applies to both releases.

**Audit disposition:** no difference; no implementation candidate is warranted from this slice. The bounded result does not claim that every movement operation in the pair has been inventoried or audited.

## Phase-two independent comparison

Compared against the authorized author report `c-yaw-precision-1151-1152-source/report.md`, whose SHA-256 was `7daca7140d1689d107f0d9047fb3e5105c26f5e72fc05800e0e0885d73818a07` both before and after this append. Its source-cited result agrees with this independent no-difference verdict. I verified the additional reachability details it supplies: `LocalPlayer.isEffectiveAi()` returns true in both releases; `Player.travel` delegates to `super.travel` on the ordinary non-fall-flying route; dry `LivingEntity.travel` reaches the same ground/air `moveRelative`; and `MouseHandler.java`, `Mth.java`, and `Vec3.java` each have identical full-file SHA-256 in both releases. The report's lawful one-pixel mouse witness is consistent with the code: with sensitivity 0.5F, smoothing off, captured/active mouse, and a local player, the finite input produces `j=1.0`, then both `yRot` and `yRotO` advance from zero to `0.15F`. No disagreement found. The assigned slice remains closed as no difference, not as full-pair coverage.