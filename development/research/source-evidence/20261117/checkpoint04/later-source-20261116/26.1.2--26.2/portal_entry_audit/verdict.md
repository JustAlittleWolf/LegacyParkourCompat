# Independent portal-entry audit verdict

Report audited: `build/later-source-20261116/26.1.2--26.2/portal_entry/report.md`

Report SHA-256: `E22B3754E30EBD9DBCF27D72D75C0D7F66C7DC074308647B1228551282E0AABB`

## Verdict

The report's bounded endpoint conclusion is supported by the exact decompiled source trees: within the inspected portal-entry callbacks, portal timer/cooldown operations, LocalPlayer portal-effect path, and transition consumer/position writers, I found no player-behavior delta between 26.1.2 and 26.2. This is a source-tree-level verdict only. It does not accept a full pair, close portal/world/shape dependencies, or establish intermediate patch onset. The exact source-input provenance remains unverified because the manifest-declared client/library artifacts are absent.

## Integrity and provenance

Both ready markers declare the requested release and `unobfuscated` namespace. Their source-manifest hashes and artifact-manifest hashes match the sidecars. I independently SHA-256 checked every source-manifest entry: 6,882/6,882 for 26.1.2 and 7,055/7,055 for 26.2. All cited Java files in both releases match their respective source-manifest entries: `NetherPortalBlock.java`, `EndPortalBlock.java`, `PortalProcessor.java`, `Entity.java`, `LocalPlayer.java`, `BlockPos.java`, and `Vec3.java`.

The 26.1.2 artifact manifest has 78 entries and the 26.2 manifest has 91; none of the referenced JARs are present under the recorded ready/output roots or the checked campaign artifact root. Both ready-marker output paths are absent. The 26.1.2 provenance points to a full Gradle log that is absent from its referenced staging location. The 26.2 provenance records a summary excerpt and `mappingArtifacts: null`. Thus the source payload hashes are intact, but the published source trees cannot be independently linked to the exact input JAR bytes from this checkout. The report accurately identifies this limitation; it should remain an open evidence caveat.

## Source audit

- Nether and End `entityInside` callbacks retain the `canUsePortal(false)` gate and the same `setAsInsidePortal` behavior. The End callback's server-side credits branch is also unchanged (`NetherPortalBlock.java`; `EndPortalBlock.java`).
- `PortalProcessor.java` is byte-identical across the pair. `Entity`'s portal cooldown writers and `handlePortal` retain the same gates and order: `handlePortal` proceeds only for a `ServerLevel`, then decrements cooldown, advances/decays the processor, obtains the destination, and applies the same transition checks.
- `LocalPlayer.aiStep` keeps portal effect handling and local cooldown decrement under the `LevelLoadingScreen` exclusion. `handlePortalTransitionEffect` keeps the same portal-process/inside-this-tick gates, screen eligibility check, container close, intensity steps, sound, and inside-this-tick clear. The 26.2 GUI accessors read/write the GUI's stored screen; this is an ownership/API move in the inspected path.
- The reported `Entity` transition range (26.1.2 lines 2981–3144; 26.2 lines 3052–3215) compares identically after trimming line whitespace. Nether exit construction and transition-relative writes are unchanged in the inspected methods. For End destinations, replacing `BlockPos.getBottomCenter()` with `Vec3.atBottomCenterOf()` is equivalent: the former delegates directly, and the latter retains `atLowerCornerWithOffset(pos, 0.5, 0.0, 0.5)`.

The source path also distinguishes local presentation from teleport execution: LocalPlayer handles the client-side portal effect/cooldown path, while `Entity.handlePortal`'s transition execution is server-gated. Server/client protocol applicability, portal shape and world/registry-dependent selection, and the remaining pair inventory therefore stay open as the report states.

## Disposition

Retain the bounded no-delta result as independently corroborated against the checked source payload. Keep source-input/JAR provenance, portal shape/world dependencies, server/client applicability, remaining coverage, and pair freeze open. No implementation, runtime-parity, or pair-closure claim is made.
