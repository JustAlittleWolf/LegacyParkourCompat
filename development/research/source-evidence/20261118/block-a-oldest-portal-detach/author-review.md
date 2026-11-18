# Independent audit of immutable author evidence

## Input identity

Read the committed bytes of `portal-oldest-detach-route:evidence.md` at commit `3ce76268c200a4d5830714f8a8f871abcd2f8298`, using `git cat-file blob`. Their SHA-256 is `b5e5c65cd869465f2af1d1166598f02744c8ad44f6da55439adc828c6c628eae`, matching the requested identity. The route below was independently frozen in `evidence.md` at commit `6c63f0b9c73e4057bc836aa17a3e3709c0f226f4` before opening that input.

## Verdict

**Agrees.** The author's conclusion matches the independently traced virtual dispatch and version-specific detach handlers. In 1.8.9 the local attach receiver invokes virtual `startRiding(null)`, and the `LivingEntity` override's existing-vehicle/null-target branch clears mount links on the client without calling the base writer. With no old vehicle it falls through, but the base position guard is false. This applies to boats because the detach branch is not conditional on vehicle class. No portal check occurs in these examined routes. In 1.9 and 1.12.2 an empty passenger list is applied by `yeetPassengers`, whose local-player `stopRiding` path only clears mount links/passenger roster and cooldown; it does not reposition XYZ or AABB.

## Identity checks

Recomputed source-file SHA-256 values agree with the sidecar indexes/manifests for every artifact-claimed route source reviewed:

- 1.8.9 attach packet, handler, LocalClientPlayerEntity, ClientPlayerEntity, PlayerEntity, LivingEntity, and Entity all match `decompiled_minecraft/1.8.9/ornithe-feather.sources.sha256`.
- 1.9 passenger packet, handler, LocalClientPlayerEntity, and Entity all match the mapping-local `.decompile-provenance.json` source hashes.
- 1.12.2 passenger packet, handler, LocalClientPlayerEntity, and Entity all match `decompiled_minecraft/1.12.2/ornithe-feather.sources.sha256`.

The receiver, active virtual method body, guards, inherited chain, and writer roster are supported by exact source bodies. No mismatch or unresolved gate remains within the assigned bounded scope.
