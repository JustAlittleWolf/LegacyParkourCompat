# Independent verdict: 1.21.1 → 1.21.2 swim pitch vertical slice

## Bound claim and evidence identity

Reviewed the source draft `c-swim-pitch-vertical-1211-1212-source/report.md`, exact SHA-256 `63e508f3ee17a6ee981b7ebd9db2d828d04d19059c256632187f12809adfed4f`. The cited source files are exact Mojmap decompilations in the primary checkout. I recomputed all ten source-file SHA-256 values and matched all five files per release to the 1.21.1 rooted source manifest / 1.21.2 embedded provenance manifest.

1.21.1 readiness identity: `mojmap.ready.json` SHA-256 `988b38ac59491f9430d5bd1968db0fdaacb777a63fef745ca3e20342237a0310`; it reports status `ready`, version `1.21.1`, `mojmap`, 5,363 source files, and names source-manifest hash `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`. The manifest itself has that hash. Its provenance file hash is `6f84a596ea944a1d40c79b53ead9d2cdd2b3c726d710a977758d15b5578ce7a9`.

1.21.2 has no rooted ready marker. Its embedded `.decompile-provenance.json` hash is `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575`; all five cited source paths and hashes match its `sources` object.

## Verdict

**Accept the no-difference claim for the assigned nonpassenger swim correction and its pitch-to-look numeric path. Do not accept a reachable ordinary-input swim behavior difference from this evidence.** The correction in `Player.travel(Vec3)` has the same `isSwimming` nonpassenger eligibility, `lookY < -0.2` factor choice, ordered short-circuit predicate, `0.085`/`0.06` factors, and double velocity-Y update in both versions (`Player.java` 1.21.1 lines 1456–1465; 1.21.2 lines 1445–1457). The query spelling changes from `getBlockState(...).getFluidState()` to `getFluidState(...)`; the exact level helper remains in the draft's cited bounds.

`getLookAngle()` still calls `calculateViewVector(getXRot(), getYRot())`. Pitch-to-radians is float in both; the returned Y is the same negated float sine widened in `Vec3`. The `Mth` sine table has the same 65,536 float entries, ascending initialization expression/order and cast, and `sin`/`cos` scale, float arithmetic, int truncation and mask in both (`Mth.java` lines 28–54). The ordinary `Entity.turn(double,double)` path calls `setXRot` and then clamps the stored value to `[-90,90]` in both (`Entity.java` 1.21.1 lines 401–409; 1.21.2 lines 412–420). Therefore ordinary mouse-turn input does not reach the changed setter behavior with an out-of-range pitch.

The draft correctly identifies a separate method-level change: finite `Entity.setXRot(float)` inputs were assigned unchanged in 1.21.1 (lines 3363–3368), while 1.21.2 assigns `Math.clamp(input % 360.0F, -90.0F, 90.0F)` (lines 3489–3494). If some caller supplies an out-of-range value directly, pitch and thus the LUT-derived look Y can differ. That is a distinct setter operation and a conditional downstream consequence, not a change to swim math. This bounded audit does not establish that an ordinary legal in-game player input can drive that path. The familiar `turn` route cannot. Keep setter/reload/network/external paths outside the accepted movement claim unless separately bounded and sourced.

The inherited water branch still executes the player movement call `move(MoverType.SELF, getDeltaMovement())` in both releases; 1.21.2 extracts the fluid path into `travelInFluid` without changing that call/order (`LivingEntity.java` 1.21.1 lines 2091–2136 and 1.21.2 lines 2178–2248). This audit covers that local movement handoff only, not full pair coverage or runtime parity.

## Exact source byte identities

| Mojmap source | 1.21.1 SHA-256 | 1.21.2 SHA-256 |
| --- | --- | --- |
| `net/minecraft/world/entity/player/Player.java` | `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71` | `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de` |
| `net/minecraft/world/entity/Entity.java` | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` |
| `net/minecraft/util/Mth.java` | `aa40272f0f980438ca7e8c874ceedc664feb77958b162d7aa3ed01f233bd2deb` | `98dfb57e239348cf71bf00695b84299232bd5e0cfe206ee8c23a69b72f1099da` |
| `net/minecraft/world/entity/LivingEntity.java` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` | `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52` |
| `net/minecraft/world/level/Level.java` | `f698b412b7e849f5076ae268f7819247b623afc905b8d59de9eb60b414e11e96` | `ff4d57a3df0b850ba02dc798c6aa6734174790b9fd17673261f33f03d44821e1` |

## Question for scope owner

Is there a specific ordinary legal player-input path, beyond `Entity.turn`, intended to reach `setXRot` with a finite pitch outside `[-90,90]`? If yes, provide or authorize that bounded caller path for a separate audit. Until then the setter delta remains an unproven conditional, not an accepted ordinary swim behavior change.
