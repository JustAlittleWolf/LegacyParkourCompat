# EXT-04: player death velocity uses a different angle conversion

- Older version A: 1.8.9
- Newer version B: 1.9.4
- Mechanic / coverage slice IDs: direct player velocity write during death handling; `EXT-04`, `INV-STATE`, `INV-EXTERNAL`
- Classification: source-confirmed floating-point expression change in a direct player velocity response
- Confidence: source-confirmed for the paired `PlayerEntity.die()` assignments; no post-death motion trajectory evaluated
- Applicability: `PlayerEntity.die(source)` runs; the angular horizontal response is selected when `source != null`
- First changed release: unknown within (1.8.9, 1.9.4]
- Runtime validation: not performed

## Paired evidence

- A `PlayerEntity.die(DamageSource)V`, lines 512-531, sets vertical velocity to `0.1F`; when source is non-null it sets X/Z from `damagedSwingDir + yaw`, multiplied by `(float) Math.PI` then divided by `180.0F`, then multiplied by `0.1F`. With null source, it sets horizontal velocity to zero. SHA-256 `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88`.
- B `PlayerEntity.die(DamageSource)V`, lines 483-502, makes the same assignments and branches, but multiplies by `(float) (Math.PI / 180.0)` before the final `0.1F` factor. SHA-256 `d658a0d95452d12bb7e347bfd802240eeeecaf7f938e10dcd43e2640434387f85`.

## Source-level difference

The degree-to-radian conversion has a different floating-point operation order: A converts `Math.PI` to float before multiplication/division; B divides in double precision and casts the quotient to float before multiplying. The formula feeds the direct X/Z velocity assignment. Both endpoints preserve the same vertical `0.1F` write and the same null-source horizontal-zero branch.

This records only a player velocity assignment in the death handler. Health and damage production, death/respawn lifecycle, and subsequent motion are not evaluated.

## Handoff

Source finding only. Preserve for full-pair reconciliation; no implementation or runtime claim is made.
