# F-4 mounted-player lava-box release boundary

## Accepted source input

- Finding commit: `019d5fcef181f6dde2a9630e0b882acfa99d6de9`.
- Finding path: `workflows/source-campaign-2026-10-07/1.21.11--26.1.2/findings/F-4-mounted-player-lava-box.md`.
- Finding blob: `bcd14cf6c38270229b035a85f929291254c3664b`.
- Raw finding SHA-256: `a49bfdd5e363f9faae5b99fe29cfefe393a958019460063fbb175ef2b053e034`.
- Independent bounded acceptance: commit `964ae707a85126a692de3bf9d41a6db5574867d5`, `ACCEPT` of the exact source witness only.
- Pair status: partial/active; this acceptance is not a pair freeze or runtime result.

## Boundary question

The accepted difference is established between 1.21.11 and 26.1.2: the older mounted Player LAVA scan uses its deflated Player box and reaches the Y=65 cell; 26.1.2 clips the passenger interaction box to the boat's max Y and its scan starts at Y=66. The finding explicitly leaves the first changed release unknown in `(1.21.11, 26.1.2]`.

The local read-only `build/movement-campaign-2026-10-07/ready/` directory listing contains exact source roots for 1.21.11, 26.1.2 and 26.2, but no `26.1` or `26.1.1` roots. The local `version_manifest_v2.json` contains stable release IDs `26.1`, `26.1.1` and `26.1.2` (and release-candidate IDs); metadata presence is not source evidence. The 26.1.2 ready marker confirms namespace `unobfuscated`, source manifest SHA-256 `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`, artifact manifest SHA-256 `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92`, and diagnostics SHA-256 `b4bd3875263a7155ac32cffd64cfacabc7bd54c82c5fe4082f1ef8a809024c02`.

No exact intermediate source bodies were available to inspect. Therefore this memo does not claim the earliest affected release.

## Dependency request

Preparation owner: publish verified, read-only readiness artifacts for exact stable releases `26.1` and `26.1.1`, in the published `unobfuscated` namespace. For each release, include the source tree, ready marker, source/artifact/diagnostic manifests and hashes, exact client artifact identity, and movement diagnostics. The boundary review needs at minimum `net/minecraft/world/entity/Entity.java`, `EntityFluidInteraction.java` when present, and `net/minecraft/world/entity/vehicle/boat/AbstractBoat.java`, plus callers or related fluid-scan classes needed to establish whether the accepted scan-bound behavior is present. Use the shared source-preparation owner and workflow; this implementation task did not run `decompileMinecraft`, alter the shared cache, or create fallback sources.

Preparation command requested: `decompileMinecraft --versions=26.1,26.1.1 --mappings=unobfuscated` using the campaign's designated shared output/cache pair and serialized preparation ownership. Verify each requested/resolved release ID exactly matches its target before publishing either readiness marker.

Review 26.1 and 26.1.1 against the accepted 1.21.11 and 26.1.2 bodies in chronological order, recording exact hashes, method bodies, descriptors, guards and call order. Then have an independent reviewer accept the first-changed-release conclusion before adding the implementation registration.

## Resolver consequence

At this revision, `ParkourVersion.V26_1` groups `26.1`, `26.1.1` and `26.1.2`. The change must emulate the first release that has the clipped mounted interaction-box behavior:

- If it first appears in 26.1, `V26_1` may represent the boundary.
- If it first appears in 26.1.1, the profile grouping must first split at that release.
- If it first appears in 26.1.2, the current `V26_1` key is too early and the grouping must split at 26.1.2.

The codebase resolver selects the closest applicable `emulates` key at or after the selected profile. The branch must not register this behavior until the source boundary establishes which of these cases applies. No enum edit, mechanic hook, mixin dispatch or change registration is made in this checkpoint.
