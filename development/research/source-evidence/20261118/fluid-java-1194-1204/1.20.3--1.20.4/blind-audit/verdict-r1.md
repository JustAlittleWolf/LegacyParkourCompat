# Independent author comparison verdict revision 1

## Rebound evidence identity

This revision supersedes the author-source hash pin in `verdict.md` (that file remains preserved). The prior verdict's author SHA was mistyped in its text; its own unchanged file SHA-256 is `46606b1f91a907baf867704916db4ea5003b9eb1264ef090d00f043fc1661aa9`. I re-read the authorized author artifact and hashed its exact bytes: `source/comparison.md`, SHA-256 `112b37f4daa4d7f06d786e768869ce849679a336449226c4d55d4e8519f5c314`, 7,239 bytes, strict UTF-8 without BOM, LF-only. This matches the authorized identity. The blind freeze input is unchanged: `frozen.md`, SHA-256 `aa6faaceb8a3725565cfbee24d62c837229bd8688f993be36473be5263eea4ef`, 8,480 bytes, UTF-8 without BOM, LF-only.

I verified the exact author artifact contains the same four bounded operation sections and no-difference conclusion accepted by the prior verdict. The scope, same-effective-tag/world-input condition, exclusions, and provenance claims are unchanged. Accordingly, the four separate verdicts remain accepted: (1) water/lava body admission and stored height; (2) fluid-current push and player normalization; (3) eye-fluid writer and actual local-player cached reader/order, with the coverage correction below; and (4) the first ordinary water/lava movement update, including `Player.travel`'s swimming prelude and inherited drag/gravity gates.

## Review correction retained

The actual local eye-state reader is `LocalPlayer.isUnderWater()` returning `wasUnderwater` (`LocalPlayer.java:1058-1061`). `Player.tick()` invokes virtual `updateIsUnderwater()` before `super.tick()` (`Player.java:250-251`); the inherited writer samples `isEyeInFluid(WATER)` into `wasUnderwater` (`Player.java:314-317`). Later in that same tick, `Entity.baseTick()` rewrites the eye-fluid tag set (`Entity.java:437-439, 1211-1223`). Thus the local-player read uses the previously stored eye-tag result. The author comparison omits this actual reader/update order, but the paired complete `Entity.java`, `Player.java`, and `LocalPlayer.java` files are byte-identical, so the bounded no-difference conclusion stands.

## Provenance and terminal verdict

The exact author artifact's provenance pins 1.20.3 client and Mojmap mapping inputs and 1.20.4 client, Mojmap mapping, and source-manifest identity. The 1.20.4 artifact-manifest hash recomputes to the ready record, and its client mapping entry equals the 1.20.3 mapping hash. These identities were checked for this rebinding; no other artifact or source was consulted.

The corrected author hash is now bound to the same reviewed claims and the unchanged freeze. Four bounded no-difference verdicts are accepted under equal effective fluid tags, world/fluid query results, and player inputs. This does not establish external resource/tag changes, intermediate patch boundaries, full-pair coverage, product acceptance, or runtime parity. `verdict-r1.md` conclusively supersedes the mistyped author-source pin in `verdict.md`; the older file is retained unchanged.