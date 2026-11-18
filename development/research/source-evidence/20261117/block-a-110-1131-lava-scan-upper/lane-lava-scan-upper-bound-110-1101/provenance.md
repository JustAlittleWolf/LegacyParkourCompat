# Provenance check: 1.10 / 1.10.1 Ornithe Feather

Hidden manifests found and hash-checked:

- `decompiled_minecraft/1.10/ornithe-feather/.decompile-provenance.json` — SHA-256 `df266e2c3009dd1a873c1665c30fe388078ed1c6227668e10cde87409a18c6da`. Recorded release `1.10`, Feather `1.10+build.2`; mapping artifact input hash `ad7cf6744d81d0260c9d97ddfe8250b2fdbd54fe332d4d00266b3545d8ddf8dd`; client input SHA-256 `c00f8170ebf4fd834f552246743a64885a6dffb3dd1e3a3f3dba9676b7a0af35`; Feather metadata SHA-256 `a1eb1ccff25e2b76fa1d08e9b515508e97ac4837fbcacbd7f77b3a01026d3950`.
- `decompiled_minecraft/1.10.1/ornithe-feather/.decompile-provenance.json` — SHA-256 `a9a50497930848bf0ddec732b6d424e947f643a8fa6290e01425ac2bbfce219f`. Recorded release `1.10.1`, Feather `1.10.1+build.2`; mapping artifact input hash `6e8ba44832b7b77d913962462bde55084005d5de6389232453afc0bc0339ea37`; client input SHA-256 `338b205f20c22b9c676d9d3227a5e6c5e190f30ad84d7caad035150b3e05ba72`; Feather metadata SHA-256 `8648e97ee398b11151cb2d441161629abde561afed9c933c18fbc622e2c9904a`.

The local client JAR hashes match the manifest/pin hashes, and local Feather metadata JSON hashes match their `.pin.json` entries. Per-source SHA-256 values for each inspected `World.java`, `Entity.java`, `LivingEntity.java`, `PlayerEntity.java`, and `LocalClientPlayerEntity.java` match the corresponding manifest `sources` entries. The root release directories contain no sidecar files; enumeration with `-Force` established that the manifests live inside the hidden mapping directories, so the absent root-level sidecars are not a missing input. The mapping artifact SHA is recorded by each manifest as the exact input identity; it was not independently re-downloaded or regenerated in this read-only audit.

Earlier roster hashes for the inspected source files remain valid. The corrected comparison is `comparison.corrected-r1.md`; the original `comparison.md` is retained unchanged for provenance of the correction.
