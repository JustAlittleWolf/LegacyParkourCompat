# Product readiness: fluid contact-height origin (r3)

**Status: COMPLETED — covered by existing product behavior for the bounded accepted operation; no code gap identified.** This is product correspondence only. No product edits, Git writes, builds, tests, runtime, or parity claims.

## Immutable accepted scope

- Corrected bounded source report: `build/later-source-20261116/1.21.11--26.1.2/fluid_contact_vector_revision_r3/report-r3.md`, SHA-256 `7ec7d39733b3689bd5df6c050d9a1fb819f0dc983c78e1f7188ed9b45e6efcc7`.
- Independent r3 ACCEPT audit: `build/later-source-20261116/1.21.11--26.1.2/fluid_contact_vector_r3_audit/verdict-r3.md`, SHA-256 `c0109067142cd2f8f587805493a171c04f243cd6e921d8379b9a76f3a2fd1d4f`.
- The r3 audit supersedes r2 and accepts only the bounded height-origin difference and conditional per-cell `<0.4` attenuation consequence for an ordinary non-passenger player and the same accepted fluid cell. Both gates require `fluidTop >= B.minY + 0.001`; the below-box contact sliver is withdrawn. It does not accept aggregate current or trajectory claims.

## Literal release, profile alias, and provider eligibility

- `ParkourVersion.java:105-107`: `1.21.11` maps to `V1_21_11`; `26.1`, `26.1.1`, and `26.1.2` map to `V26_1`; `CURRENT` is separate. `ParkourVersion.of` gives the exact running native release precedence (`:169-181`), so an exact native build is `CURRENT`; selecting 26.1.2 while running the 26.2 target uses `V26_1`.
- Discovery eligibility is automatic: `MovementChangeDiscovery.java:31-66,78-94,120-135`, SHA-256 `985729cbfef21344018328f115d38676d841b154470ad5009d6edf9a68975a70`, scans version subpackages, reads the actual annotation, and registers every implemented `@MechanicType` hook; this public class has the required public no-argument constructor. The provider is `src/client/java/me/wolfii/legacyparkourcompat/change/v1_21_11/FluidCurrentDepthChange.java`, SHA-256 `7c5109575284dc51f48e3d25f44d5b17dc9254ca94040fd45c9c69d8ebeeeb36`. Its authoritative annotation is `@MovementChange(emulates = ParkourVersion.V1_21_11)`. It implements the surface and depth-origin hooks independently. For applicable water/lava it returns the historical surface `(double)(blockY + fluidHeight)` and origin `rawBoundingBox.deflate(0.001).minY` (lines 32-42).
- `ChangeResolver.java:28-35`, SHA-256 `f9dd060f232bb3d34a58a55b792283a3aa01875b44e80fc454b9298075af8587`, selects the closest annotation version greater than or equal to the selected profile. Thus `V1_21_11` can resolve this provider; `V26_1` cannot resolve this earlier provider and gets no historical origin hook.

## Operation correspondence and native fallback

`FluidCurrentDepthMixin.java:23-63`, SHA-256 `5614485af98031f761f48b84e138d861c7b0aaeb75c76d2ff2c43cebc1ab400f`, captures the raw bounding box and sampled fluid height, dispatches the surface/origin hooks, and replaces only the first argument to the running `Math.max` with `surface - origin`. With the `V1_21_11` provider, this is exactly the accepted old depth expression `fluidTop - D.minY`, where `D = B.deflate(0.001)`. Its default returns are native `nativeFluidTop - rawBox.minY`; if no hook is resolved, both optionals are empty and those native arguments are retained. `MovementRuntime.appliesTo` (`MovementRuntime.java:129-134`) limits changes to client-side players; `find` (`:383-385`) returns empty for ineligible entities. Therefore ordinary historical player scans receive the corrected old height, while `V26_1`, `CURRENT`, non-player entities, and logical-server entities retain native behavior.

This correction changes only the per-cell contact-height input. The existing scan gate, eye state, and current contributor aggregation remain in their existing paths. In particular, the product's separate `FlowingWaterCurrentAggregation` (`@MovementChange(V1_13_1)`) is not the same operation as this height origin. The accepted conditional attenuation crossing is covered because the historical per-cell height passed through the running maximum is restored before the existing `<0.4` calculation; no aggregate current/trajectory result is asserted.

## Source identity and remaining gates

The corrected r3 report's source hashes were verified against the local canonical decompiled files: 1.21.11 `Entity.java` `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`; 26.1.2 `EntityFluidInteraction.java` `5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62`. Source geometry confirms `D.minY = B.minY + 0.001`, old stored height is 0.001 smaller for the same accepted cell, and newer stored height is 0.001 greater.

Remaining r3 limitations stay open: fluid/tag membership and resource overrides, equality of accepted cells across scan/preflight paths, chunk/section preflight, passenger behavior, wider callbacks, intermediate onset and pair coverage. This result confirms correspondence for the accepted bounded operation only; it does not establish integration/build success or runtime parity.

