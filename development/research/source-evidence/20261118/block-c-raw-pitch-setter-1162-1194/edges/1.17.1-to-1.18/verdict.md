# Independent source verdict: raw pitch setter before clamp

Verdict: no difference found for the assigned ordinary direct `Entity.turn` pitch path between Minecraft 1.17.1 and 1.18.0. This verdict is limited to the named operation and its live Elytra pitch consumer; it is not a full-pair coverage claim.

## Frozen published source identities

Canonical read-only roots: `decompiled_minecraft/1.17.1/mojmap/` and `decompiled_minecraft/1.18/mojmap/`.

| Published file | 1.17.1 SHA-256 | 1.18 SHA-256 |
| --- | --- | --- |
| `mojmap.ready.json` | `c7180dce94d7b57b53a964445b0c788c71f23ba3ba40eea593c11cc939185e9b` | `4537edf0e88c88631215beb1c14f1d67f5a5d6d021c982c0af549b7b27856e5f` |
| `mojmap.provenance.json` | `de197f77473955635e8c5730e113f227af2b8af9306b74e8a54a765eafc5a333` | `e642f075b25d1b6e74bad05edacd740421c70f5d8e0c2a38767523cf864124c5` |
| `mojmap.sources.sha256` | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` | `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78` |
| `artifacts.sha256` | `e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa` | `7ca2b4da88c215e52924d277b257c4631f9664f5def2fb2b7ef25479ec330234` |

Both ready markers identify the exact requested release and `mojmap`. The source-manifest and artifact-manifest SHA-256 values match the hashes recorded in each ready marker. Source counts also match both manifest row counts and on-disk Java file counts: 4,142 for 1.17.1 and 4,203 for 1.18. The 1.18 ready marker's provenance hash matches the published provenance file. The 1.17.1 ready marker has no provenance-hash field; its published provenance file was independently pinned above.

The following relevant source files match their entries in the canonical source manifest (hashes are SHA-256 of file bytes):

| Source member file | 1.17.1 | 1.18 |
| --- | --- | --- |
| `net/minecraft/world/entity/Entity.java` | `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de` | `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1` |
| `net/minecraft/world/entity/LivingEntity.java` | `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f` | `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` |
| `net/minecraft/world/entity/player/Player.java` | `724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481` | `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2` |
| `net/minecraft/client/MouseHandler.java` | `6ff70f8634171709ba08a42158aea7c8d4d3974d73f89b43bfe08a5ca6db5fb2` | `7ab775d1565a883cbfb37dbef44da9412ce3f071475cb23e8f1247785634f819` |
| `net/minecraft/client/player/LocalPlayer.java` | `c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812` | `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996` |
| `net/minecraft/client/player/AbstractClientPlayer.java` | `d0508c94f43e7e90683b8f46586665bd1e1f0dca2fe25274b852f5e6c0ce05e8` | `5b68729fb699b5c5c6a8b1b3107b25800f513614b88a42bf1d29cc5684c8e7c0` |

## Exact operation trace

- `net/minecraft/client/MouseHandler.java`, `turnPlayer()`, lines 208–245: in the ordinary active mouse path, when `minecraft.player != null`, calls `minecraft.player.turn(k, l * q)` (1.17.1 line 244; 1.18 line 244). The player reference is the local player; `LocalPlayer` extends `AbstractClientPlayer`, which extends `Player`. No `turn` or `setXRot` override appears in these three player classes in either release, so the call resolves to inherited `Entity.turn` and `Entity.setXRot`.
- `net/minecraft/world/entity/Entity.java`, `turn(double,double)`, 1.17.1 lines 377–389; 1.18 lines 383–395. Both bodies have the same operation sequence: `f = (float)e * 0.15F` (newer local name `$$2`), `g = (float)d * 0.15F` (`$$3`), `setXRot(getXRot() + f)`, `setYRot(getYRot() + g)`, `setXRot(Mth.clamp(getXRot(), -90.0F, 90.0F))`, `xRotO += f`, `yRotO += g`, then `xRotO = Mth.clamp(xRotO, -90.0F, 90.0F)`, followed by passenger notification when applicable. Pitch delta is cast to `float` before multiplication by the float literal `0.15F`; yaw follows the same cast-then-multiply pattern. The first pitch setter happens before yaw is set and before the final pitch clamp.
- `Entity.setXRot(float)`, 1.17.1 lines 3023–3029; 1.18 lines 3014–3020. Both call `Float.isFinite` first. A non-finite argument is passed to `Util.logAndPauseIfInIde(..., "discarding.")` and does not replace `xRot`; a finite argument is assigned directly to `xRot`. The setter itself has no pitch-range clamp.
- Finite above-360 witness: starting from ordinary `xRot = 0.0F`, pass finite `e = 3000.0D` and `d = 0.0D`. `(float)e` is finite `3000.0F`; `3000.0F * 0.15F` is finite `450.0F`. The first setter therefore accepts and stores `450.0F` (not infinity); after the yaw setter, the final pitch clamp stores `90.0F`. `xRotO` is likewise incremented by the finite delta and then clamped to `90.0F`. This demonstrates the raw setter/final clamp order without relying on overflow or an infinite intermediate.

## Live next active Elytra movement read

`LivingEntity.aiStep()` invokes `this.updateFallFlying()` and then `this.travel(new Vec3(this.xxa, this.yya, this.zza))` (1.17.1 lines 2544–2546; 1.18 lines 2546–2548). `Player.travel` delegates to `super.travel` in its non-creative-flight branch (1.17.1 lines 1424–1436; 1.18 lines 1424–1436), so ordinary active Elytra flight reaches the `LivingEntity.travel` branch guarded by `this.isFallFlying()` (1.17.1 line 2068; 1.18 line 2070). In that live movement branch, `getLookAngle()` reads rotation through the entity view-vector calculation, and the next explicit pitch read is `this.getXRot() * (float)(Math.PI / 180.0)` (1.17.1 line 2075; 1.18 line 2077); its cosine feeds the Elytra velocity update. Both releases contain the same pitch reads and movement arithmetic/order there. The current `xRot` observed by this next movement call is the clamped field value from `Entity.turn`, not the finite raw value that was immediately overwritten by the final clamp.

## Conclusion

No source-backed change is present in the assigned path. No finding is proposed for the 1.17.1→1.18.0 pair on this operation. Acceptance of finite raw pitch before the final clamp, non-finite setter behavior, cast/multiply order, the caller, and the subsequent active Elytra pitch consumer are materially the same.
## Phase-two exact binding and signed audit attestation

This append binds the frozen source verdict to the exact author artifacts reviewed in phase two. The pre-append bytes of this verdict had SHA-256 `6f993b31e20e07bf6af93667471f3e2b472d6622d4193d1479633c30e0714d71`. The reviewed author files were `lab c-raw-pitch-setter-1171-118-source/report.md`, SHA-256 `1ad1213578fd086fa7b199e11596ce1773667242714f383e8005efd44066764b`, and `identity.md`, SHA-256 `fa7460cb8d8eefab0f4608591dd518ca93879d4df059ecf0487834dcf1b2e39c` (both hashes verified before opening the files).

**Comparison verdict:** The author's corrected conclusion agrees with this independent verdict: the compared ready Mojmap source trees show no difference in direct `Entity.turn` pitch response, finite setter behavior or final clamp. Its identity correction supplies root ready/provenance sidecar and manifest identities consistent with the independently pinned identities above. The author's report's initial nested-manifest limitation is therefore not evidence against the source comparison; its correction explicitly distinguishes nested `.decompile-provenance.json` from the version-root ready/provenance sidecars. The author also records the missing raw-cache rehash as a limitation; this audit did not inspect or rehash that cache.

The numeric witnesses are compatible but differ in magnitude: the author's `turn(0.0, 1000.0)` witness produces finite `150.0F`, while this verdict's `turn(0.0, 3000.0)` witness produces finite `450.0F` before the setter's subsequent clamp to `90.0F`. The latter directly covers the assigned finite post-cast pitch above 360 degrees and does not rely on infinity. Both establish identical observed final pitch in the two releases. The compared live active Elytra consumer is likewise consistent: travel reads the pitch after `turn` has completed and applies it in the glide calculation.

Signed audit attestation: `/root/c_raw_pitch_setter_1171_118_audit`, assigned independent source auditor. I attest that this binding records my comparison of the exact frozen verdict and the two author files identified above; the no-difference verdict remains unchanged.