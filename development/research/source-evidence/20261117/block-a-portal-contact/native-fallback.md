# Nether portal native fallback source audit

Terminal status: COMPLETED

## Verdict

The bounded native shape-provider and identity-sentinel claim is supported for all eight inspected pairs: 1.21.8, 1.21.9, 1.21.10 and 1.21.11 Mojmap; 26.1, 26.1.1, 26.1.2 and 26.2 published unobfuscated sources. In each exact source tree, NetherPortalBlock extends Block and has no entity-inside collision-shape override. Block's inherited provider reaches BlockBehaviour.getEntityInsideCollisionShape, whose default returns Shapes.block(); BlockState's method delegates to its block. Entity's visitor compares the returned shape by reference to Shapes.block() before attempting the translated-AABB swept intersection. Thus a portal candidate that reaches this visitor uses the identity shortcut. The portal's thin `getShape` is an outline/ordinary block shape path and is not the entity-inside provider. The portal callback retains `canUsePortal(false)` then `setAsInsidePortal`; the later callback precision parameter is not consumed by NetherPortalBlock.

The bounded later-adjacency claim also holds: the cited provider, identity-predicate and portal-callback slices show no behavior change across 1.21.10→1.21.11, 1.21.11→26.1, 26.1→26.1.1, 26.1.1→26.1.2 or 26.1.2→26.2. The six relevant files are byte-identical across 26.1/26.1.1/26.1.2; 26.2's source has shifted names/lines but retains the same provider, sentinel and callback behavior. I found no conflicting later provider behavior in the assigned sampled releases.

This establishes the native shape-provider fallback behavior for these sampled source releases. Returning the thin outline, constructing a geometrically equivalent box, or imposing a precision condition in the provider would not preserve the observed singleton identity path. It does not establish that the broader portal-contact path is otherwise version-invariant.

## Source identity and integrity

The submitted checkpoint `portal-native-fallback/comparison.md` had the assigned SHA-256 `ec2932651f1cf1282e1630d17130408614ae7a3ca2df66e92e387d26117de330`.

Canonical source roots read-only: `decompiled_minecraft/<version>/mojmap/net/minecraft/` for the four 1.21.x releases and `decompiled_minecraft/<version>/unobfuscated/net/minecraft/` for the four 26.x releases. The checkpoint's cited files (`NetherPortalBlock.java`, `Blocks.java`, `BlockBehaviour.java`, `Entity.java`, `BlockGetter.java`, `Shapes.java`) exist in every root. I recomputed their SHA-256 values and matched all six against each release's source sidecar for seven releases. For 1.21.9, all six matched the embedded `mojmap/.decompile-provenance.json` `sources` hashes. The mapped-source hashes therefore match the published local manifest identities for all eight releases.

For 1.21.8, 1.21.10, 1.21.11 and all four 26.x releases, the ready marker's source-manifest and artifact-manifest hashes match the sidecars. Ready markers identify the exact version, mapping (`mojmap` or `unobfuscated`) and client digest. Their artifact manifests pin version metadata and raw client; the 1.21.x Mojmap sidecars also pin the official client-mapping text and mapped client. The 26.x mapping profile is unobfuscated and has no separate mapping artifact. In 1.21.9, the embedded provenance records pipeline `lpc-decompile-v1`, Java 25.0.3+9-LTS and Vineflower 1.12.0, hashes the full source tree, and pins the exact version metadata, raw client and official Mojang client mapping input. I recomputed and matched the six relevant source entries. The 1.21.8 ready marker's output path refers to a campaign build directory rather than the canonical source directory, but its canonical source sidecar hashes match its cited files.

Declared downloaded artifact files were not available at the local campaign-cache paths checked, so I could not independently rehash the raw client/mapping archives. Their declared SHA-256 pins are recorded in the publication sidecars/provenance; this audit directly rechecked source-file bytes and manifest identities.

## Scope and caveats

- The 1.21.8→1.21.9 candidate enumeration changed, and the later corner-based traversal is visible in the sampled native releases. That can change which portal candidates reach the unchanged provider/callback; provider parity does not imply traversal parity. The source-derived contact example in the checkpoint remains a prediction, not runtime evidence. Traversal is noted only as a dependency and is not promoted here into a generic traversal finding.
- In 1.21.10 and later, the entity-inside callback receives a precision boolean. The portal callback body ignores it; this does not prove every callback or consumer ignores the flag.
- Candidate reachability, portal state consumers, logical-client contact details, teleport authority, disconnected movement paths and whole-pair inventory/freeze remain outside this bounded provider audit. No runtime/game/TAS verification was performed.

## Disposition

Keep the assigned bounded finding as a native fallback/provider invariant across these sampled releases. Retain the separate candidate-enumeration difference and its traversal dependency as stated in the checkpoint. No source-derived provider change is indicated by this slice.