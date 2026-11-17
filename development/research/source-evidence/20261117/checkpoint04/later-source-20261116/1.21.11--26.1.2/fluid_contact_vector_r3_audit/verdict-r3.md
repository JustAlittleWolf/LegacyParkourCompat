# Independent source audit: fluid-contact revision r3

Verdict: **ACCEPT bounded corrected claim**. This audit binds the exact r3 report SHA-256 `7EC7D39733B3689BD5DF6C050D9A1FB819F0DC983C78E1F7188ED9B45E6EFCC7` at `build/later-source-20261116/1.21.11--26.1.2/fluid_contact_vector_revision_r3/report-r3.md`.

## Exact identity and scope

The report correctly uses canonical root sidecars, not nonexistent hidden sidecar paths. 1.21.11 Mojmap provenance is `decompiled_minecraft/1.21.11/mojmap.provenance.json` SHA-256 `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72`; ready marker `mojmap.ready.json` SHA-256 `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b`; source manifest `mojmap.sources.sha256` SHA-256 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`. 26.1.2 unobfuscated provenance is `decompiled_minecraft/26.1.2/unobfuscated.provenance.json` SHA-256 `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a`; ready marker `unobfuscated.ready.json` SHA-256 `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7`; source manifest `unobfuscated.sources.sha256` SHA-256 `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`. Sidecar contents identify version/mapping pairs as claimed and ready source counts match 6,622 / 6,882.

I rechecked the corrected report bytes and the source gates that determine the changed math. Current hashes match both the report and source-manifest rows: 1.21.11 Mojmap `Entity.java` `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`; 26.1.2 unobfuscated `Entity.java` `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`; `EntityFluidInteraction.java` `5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62`. Unchanged source inventory/formula checks from r2 are reused from its independent ACCEPT verdict; this revision did not trigger a whole-tree rehash.

## Corrected claim audit

- `AABB.deflate(0.001)` raises the contracted box's `minY` by `0.001`: `D.minY = B.minY + 0.001`.
- 1.21.11 scans this `D` and accepts a fluid cell only when its top is at least `D.minY`; its accumulated height is `max(fluidTop - D.minY, 0)`.
- 26.1.2's ordinary non-passenger interaction box is likewise `B.deflate(0.001)`. Its scan condition `!(fluidTop < box.minY)` is equivalent to `fluidTop >= D.minY`; it captures `entityY = B.minY` and accumulates `max(fluidTop - entityY, priorHeight)`.
- Thus both contact gates reject `[B.minY - 0.001, B.minY)`. The r2 contact-state/sliver argument is correctly withdrawn. For the same accepted cell establishing the corresponding running maximum, the newer height is exactly `0.001` greater than the older height. This sign is correct.
- Both examined current paths scale a flow contribution by their running height only below `0.4`. For a same accepted cell establishing the relevant maximum, if old `h < 0.4 <= h + 0.001`, the old contribution is scaled by `h` and the newer contribution is unscaled. At `h=0`, the old contribution is zero and the newer scale is `0.001`. This is conditional per-cell math only. The report does not claim an aggregate current or trajectory result.

## Limits and disposition

ACCEPT only the bounded source-level height-origin difference and stated conditional per-cell attenuation consequence. Resource/tag membership, equal accepted-cell preconditions across scan paths, chunk/section preflight, passenger behavior, wider callbacks, pair coverage, onset, and runtime parity remain open. No whole-pair freeze follows. The independent r2 verdict was specifically changed by the sign/sliver corrections; acceptance here applies to r3 bytes only.

No backlog/wiki/product/Git-history review, artifact inspection, build, test, launch, or source generation was performed.
