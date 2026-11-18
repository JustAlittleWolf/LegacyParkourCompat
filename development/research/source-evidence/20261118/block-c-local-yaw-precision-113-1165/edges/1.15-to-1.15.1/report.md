# 1.15 → 1.15.1 direct local yaw and dry input path

## Exact source identity

Both sides are exact releases, official Mojmap, prepared in the same provenance run `prep-1.15-1.15.1-mojmap-2026-10-09-r1` with Vineflower 1.12.0. The per-release provenance records identify 1.15 metadata SHA-256 `d154eaf07885a7fd6dfc6014bb9caf08fecc19ccd9a0862b31b1c7d1639e7785`, client JAR SHA-256 `41eee66b4c8e76c018745a63a46a15097cc1c6482c94b38801054938a010b21b`, and official client mappings SHA-256 `11415bc25024658f0ef1c1a0028df282135299e526a689de2f29c4662edf931a`; 1.15.1 metadata SHA-256 `dda0859bca7447ff5f071643006b35a51f54e2fbef851a6665b424cf5743c598`, client JAR SHA-256 `6e28ecd008d0ca5e1dcd346dbc2882430f02a77a78864d57acc49fc8bc1b5358`, and official mappings SHA-256 `f46186d0ed9d17e073e16ae2c98675dbbc70b4a29495700837aa5de9c1b42c34`.

The complete source-manifest SHA-256 is `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340` for 1.15 and `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d` for 1.15.1; each matches the manifest hash recorded in its provenance. The verified source-file SHA-256 values are byte-identical across releases:

- `net/minecraft/world/entity/Entity.java`: `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`
- `net/minecraft/world/entity/LivingEntity.java`: `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79`
- `net/minecraft/client/player/LocalPlayer.java`: `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823`
- `net/minecraft/world/entity/player/Player.java`: `6985e11ad9be553e9a2f25e5decc20a9da5c3b59b68f4e83a5b9f448cd2e48a0`

These are the manifest-listed source bytes, independently checked against both on-disk trees. No regeneration or network lookup was used.

## Direct yaw writers and turn

In both releases, `Entity.java` has byte-identical bodies for `setRot(float,float)` (lines 310–313), `turn(double,double)` (326–337), `absMoveTo(...)` (1094–1103), and `moveTo(...)` (1111–1115). `setRot` applies `f % 360.0F` directly to current yaw. `turn` computes both scaled mouse deltas as `double` (`e * 0.15`, `d * 0.15`), adds the yaw delta to current `float yRot` with double promotion and casts the result to float, clamps pitch, then updates old pitch and old yaw in that order; there is no yaw normalization or finite-value gate. `absMoveTo` stores `g % 360.0F` in current yaw, then copies current yaw to `yRotO`; `moveTo` assigns `g` directly to current yaw and does not update `yRotO`. The same operations, casts, order, and absence of finite gates are present on both sides.

Finite witness for the ordinary `turn` path: finite input `d=8.0`, `e=4.0`, current yaw `10.0F`, and previous yaw `11.0F` produce double intermediates `g=1.2` and `f=0.6`; current yaw is assigned `(float)(10.0F + 1.2)`, then previous yaw `(float)(11.0F + 1.2)`. This follows the source operation order without invoking any NaN or infinity behavior. It is a witness for the actual arithmetic shape, not a claimed release difference.

## Reachable ordinary dry ground/air yaw contribution

The local player's `serverAiStep` copies `input.leftImpulse` / `input.forwardImpulse` into `xxa` / `zza` when the camera controls that player (`LocalPlayer.java` 603–616); `LocalPlayer.aiStep` calls `super.aiStep()` (778), and `LivingEntity.aiStep` forms the travel vector after multiplying `xxa` and `zza` by `0.98F` (`LivingEntity.java` 2282–2287). In `LivingEntity.travel`, with water and lava false and fall-flying false, the ordinary dry branch reads block friction and calls `moveRelative(getFrictionInfluencedSpeed(v), vec3)` (1831–1837, 1867–1891). Its speed is `onGround ? getSpeed() * (0.21600002F / (v * v * v)) : flyingSpeed` (2006–2008). Thus the ordinary grounded and airborne routes both pass their finite movement input and speed into the same yaw-bearing operation.

`Entity.moveRelative(float,Vec3)` passes `this.yRot` to `getInputVector` and adds the result to delta movement (1063–1066). `getInputVector` returns zero only when input length squared is below `1.0E-7`; otherwise it normalizes only above length squared `1.0`, scales by the supplied speed, converts yaw using `g * (float)(Math.PI / 180.0)`, then applies float `Mth.sin` / `Mth.cos` to the horizontal input (1068–1077). The source is byte-identical in 1.15 and 1.15.1.

Finite reachable arithmetic witness: forward input `(0,0,1)`, yaw `90.0F`, ordinary default player movement speed `0.1F` (Player.java registers this base attribute at lines 191–194), and default `flyingSpeed=0.02F` (LivingEntity.java line 150). For the grounded case use ordinary stone friction `v=0.6F`; for the airborne case `onGround` is false. All values are finite; the nonzero input reaches the trig path (`lengthSqr()==1`, so no normalization). Its yaw contribution is horizontal `x=-speed*sin(yawRadians)`, `z=speed*cos(yawRadians)` (plus the preexisting delta movement). This demonstrates the actual dry ground/air input-to-yaw dependency without using an out-of-scope vehicle or synthetic replacement path.

## Verdict

No source difference was found in the assigned yaw setter/turn operation or its first ordinary dry ground/air speed-input yaw contribution for 1.15 → 1.15.1. The current-versus-previous writes, widths, casts, gates, travel branch, speed calculation, and yaw transform are the same exact source bytes. This closes only this bounded slice; it is not a full-pair movement inventory or runtime-parity claim.