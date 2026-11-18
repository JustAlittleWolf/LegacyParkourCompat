# 1.16 → 1.16.1: direct `Entity.turn` pitch audit

## Bounded verdict

**No source delta found for ordinary `Entity.turn` pitch storage or its immediately reached Elytra pitch response.** The canonical Mojmap `Entity.java`, `LivingEntity.java`, and `LocalPlayer.java` files are byte-identical across 1.16 and 1.16.1; the paired ready source manifests also list identical SHA-256 values for these files. This verdict covers the assigned operation and its active local Elytra consumer only; it is not a full-pair coverage freeze.

The exact method body in both versions is `Entity.turn(double d, double e)` at `net/minecraft/world/entity/Entity.java:332–344`:

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

The widths and order are significant: `e` is a `double`; multiplication by unsuffixed `0.15` stays `double`; adding to the `float` rotation promotes that operand to `double`; the explicit cast narrows the result to `float` before the field write. `d` and yaw are handled next, then the current pitch receives the outer float clamp. Previous/interpolated pitch `xRotO` is updated afterward from its own old value using the same double delta and cast, then separately clamped. The method does not call `setRot` or wrap either turn delta modulo 360. `setRot(float,float)` at `Entity.java:316–319` does `% 360.0F`, but it is not in this path.

A finite ordinary method-input witness for the requested post-cast boundary is `xRot = 0.0F`, `e = 2401.0`: `f` is the double `360.15`, the casted field assignment is the nearest `float` to `360.15` (greater than 360), and the immediately following `Mth.clamp(..., -90.0F, 90.0F)` stores `90.0F`. `Mth.clamp(float,float,float)` at `net/minecraft/util/Mth.java:94–100` performs `f < g ? g : (f > h ? h : f)`. Thus finite overflow to positive or negative infinity reaches the corresponding endpoint, while NaN passes through unchanged because both comparisons are false; there is no separate finite-value setter/guard before or in this `turn` sequence. The same applies independently to `xRotO`.

## Local call and next Elytra pitch read

`net/minecraft/client/MouseHandler.java:240–243` invokes `this.minecraft.player.turn(j, k * n)` when a local player is present. `LocalPlayer.tick()` (`net/minecraft/client/player/LocalPlayer.java:183–185`) calls `super.tick()`; the inherited `LivingEntity.tick()` calls `this.aiStep()` at `LivingEntity.java:2168`. `LocalPlayer.isEffectiveAi()` returns true (`LocalPlayer.java:519–520`), so `LivingEntity.travel()` passes its `isEffectiveAi() || isControlledByLocalInstance()` gate (`LivingEntity.java:1912–1914`) for the active local player. `LivingEntity.aiStep()` reaches the travel phase, calls `updateFallFlying()`, then `this.travel(...)` (`LivingEntity.java:2452–2457`).

When the fall-flying branch is active (`LivingEntity.java:1977`), its first pitch-dependent operation is `this.getLookAngle()` at line 1983; `Entity.getLookAngle()` (`Entity.java:1680`) builds the vector from `this.xRot` and `this.yRot`. The next explicit pitch read is `float j = this.xRot * (float)(Math.PI / 180.0)` at `LivingEntity.java:1984`, feeding the glide cosine/sine calculations below. The local flight-start path checks the chest Elytra and calls `tryToStartFallFlying()` in `LocalPlayer.java:750–754`; after the fall-flying state is active, the source call chain above reaches the actual travel consumer. No additional pitch normalization is inserted between `turn` and this read by these methods.

## Canonical evidence identity and readiness

Exact pair: Minecraft **1.16 → 1.16.1**, Mojmap on both sides. Canonical source roots: `decompiled_minecraft/1.16/mojmap/` and `decompiled_minecraft/1.16.1/mojmap/`.

The frozen identity tuple sent before implementation access has SHA-256 `d495b14db8d2bab038428d72ab33fd9aba94e738b94483d1317f481a18bc81a6`. It binds both releases' ready/provenance/source-manifest/artifact-manifest hashes and the paired `Entity.java`, `LivingEntity.java`, `LocalPlayer.java`, and `ElytraItem.java` source hashes. The extra active-call-support files have these matching paired hashes, corroborated by the per-release source-manifest rows:

- `net/minecraft/client/MouseHandler.java`: `2611dceb1078a63a33d621bf02f466ae70f6d015c902813935b9836f39415949`
- `net/minecraft/util/Mth.java`: `0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290`

Both `mojmap.ready.json` markers say `status: ready` and their version IDs match their directories. For 1.16, marker SHA-256 is `5b6cb24f95e83b856c53f646d6477f2e46dae2276737dee56dd07c5bd745e4a2`; source-manifest SHA-256 `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a`; artifact-manifest SHA-256 `b482d7aa44422dfafebad3a0b2a886d47d1f01135f99055aea5973faa110e8b2`; provenance SHA-256 `371d50521a183f4a3f42fac2d18021f228e0a4e6b2e8a9838c2805391709f2a9`. The marker lists 3,572 source files and matching manifest hashes. Its provenance records resolved version 1.16, Mojmap, client SHA-256 `00740ef4475d4cd0a1d7064f89602f759c45ff9afe29da9b0ee0b9037ebb2a71`, mapping file SHA-256 `53d46abe149aadd367be8789207c53a7cee46ba8382e5bfd60247eb22984119d`, mapped-client SHA-256 `3213f0827fa9215f41e74ca1dedaf104738e9c70ea1635769fb90f3996e4bf6c`, and Vineflower 1.12.0.

For 1.16.1, marker SHA-256 is `c19d707f7d6622733b5189599ff77aa7999cc11a3d900f29d150f9fab203ffcb`; source-manifest SHA-256 `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754`; artifact-manifest SHA-256 `a113f9d59fa4582effb2ebf548c4388a54c9c5654fbf57fae7974dce896d1f98`; provenance file SHA-256 `a6ff065188a400ec891980cdf2e6c0c238bf5e05168a2f69b29c6cecc2214b98`. The marker lists 3,572 source files and matching source/artifact manifest hashes. Its provenance records resolved version 1.16.1, Mojmap, and Vineflower 1.12.0, but has no nested artifact identity object. The matching `artifacts.sha256` manifest supplies the 1.16.1 raw-client SHA-256 `b4831e7b63b10588ff06ea86322108d916c536446f3aea3ae988b7fe3e1f66ef`, mapping file SHA-256 `6cc374f819bca44d940f8912044b07bdac3e240a02e37117ae1d4ad63c21409b`, and mapped-client SHA-256 `f92d3ad192db28f84522436cc12bb92498ef3b8090f88e906d56d1fb8744490d`. Unlike the 1.16 marker, the 1.16.1 ready JSON does not carry a `provenanceSha256` field; the provenance file's actual hash is reported above rather than treating that relationship as marker-asserted.

Per-file source-manifest rows agree on both sides:

- `Entity.java`: `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576`
- `LivingEntity.java`: `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26`
- `LocalPlayer.java`: `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b`
- `ElytraItem.java`: `d20a679b8822eaa57e5216ce14ed755c1f86a160eb5dad0dc9e3d8f4b5ea018d`

The source hashes were independently recomputed from the canonical files and match their manifest rows. The 1.16 and 1.16.1 provenance/ready markers remain distinct even though the audited operation and call-path source files have equal content hashes.


## Phase-two comparison: frozen author report

The author report at `c-raw-pitch-setter-116-1161-source/report.md` was rehashed before comparison and matches the supplied SHA-256 `c6fcf05cbbdc01fd9a661958ce9728a0831aa290bff07d743e32e2341d987bf0`.

The report agrees with this verdict on the bounded outcome: 1.16 and 1.16.1 have no `Entity.turn` pitch-response delta; the pitch delta is double through multiplication/addition, narrows to float before the outer `[-90F, 90F]` clamp, and is not modulo-wrapped by `turn`; an ordinary finite post-cast value over 360 is clamped before the active Elytra calculation reads current pitch. Its alternate finite witness (`e = 2401.25`) also follows the stated source math. The report additionally binds `Player.java` and discusses `absMoveTo` and explicit-angle `moveTo`; those adjacent entry points are outside the initial frozen identity and operation scope of this audit.

Call-chain qualification: my earlier wording that `LivingEntity.aiStep()` reaches `LivingEntity.travel()` omitted virtual dispatch through the player override. The author report identifies the active route as `Player.travel` → `LivingEntity.travel`, with the actual fall-flying pitch consumer in the latter. My independently frozen trace established the local tick/AI-step call and the `LivingEntity.travel` consumer, but did not freeze the `Player.travel` wrapper body or Player.java hash. The report's extension is consistent with the same consumer and does not alter the no-difference verdict; that wrapper detail is attributed to the frozen author report here rather than claimed as independently retraced in phase two.

The original verdict prefix was preserved byte-for-byte. Prefix SHA-256 before and after append: `9c3031bc370bbcffe3b307553b587fd62d6608146114fd005ae8fad88bd3b5d2`.