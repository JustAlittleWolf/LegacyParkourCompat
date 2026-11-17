# Product readiness: 1.8.9→1.9 yaw-angle precision

Status: COVERED_NO_CODE for the bounded accepted `Entity.updateVelocity` angle-expression claim; no runtime-parity or full-pair claim.

## Immutable accepted inputs

- Candidate: `build/source-189-19-20261117/input-travel/movement-input-yaw-angle-precision-r2.md` — SHA-256 `698cbc7b69f3d92cfb865a53c443e77ea4db001dbe76dddb62b1f52408f12ec4`.
- Independent ACCEPT: `build/source-189-19-20261117/audit-yaw-r2/verdict.md` — SHA-256 `DD5946729B8EF0728159927C12599A6ECCE24C636BB7F08AC45849A26D4723F8`.
- Canonical finding: `development/research/findings/1.8.9--1.9.4/relative-input-angle-conversion.md`; not edited.

## Product correspondence

`HeadingVelocity` at `src/client/java/me/wolfii/legacyparkourcompat/change/v1_8_9/HeadingVelocity.java` is annotated V1_8_9 and handles the ordinary local-player dry relative-input path when `input.y == 0`. It retains the 1.8.9 angle expression `yaw * (float)Math.PI / 180.0F` before table lookup. `EntityMixin` dispatches through `HeadingVelocityHook` at `Entity.moveRelative`; when the hook handles the input it returns before the native implementation.

`NativeHeadingVelocity` at `src/client/java/me/wolfii/legacyparkourcompat/change/v1_9/NativeHeadingVelocity.java` is annotated V1_9_3 and returns false, preserving native movement. Resolver behavior maps selected V1_9 (1.9/.1/.2) to this later provider and selected V1_9_3 (1.9.3/.4) to the same provider. Its source comment identifies 1.9.4's float-cast `(PI / 180)` expression as the post-1.8.9 behavior; the accepted 1.9 report confirms that expression at the exact 1.9 endpoint. Thus both 1.9 alias groups retain native 1.9-style angle conversion.

`RelativeInputYaw @V1_8_9` is a separate hook for the wet native relative-input path and does not replace the accepted ordinary dry-path coverage above. No product code or Git state was changed for this check.