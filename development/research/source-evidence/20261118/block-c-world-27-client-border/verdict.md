# Independent source audit: WORLD-27

Verdict: **ACCEPTED — no exact bounded direct player difference is source-supported in the audited slice.**

## Immutable reviewed inputs

- Frozen source comparison: `c-world27-source/comparison.md`, SHA-256 `2c1a96caaec208131dd876098b95ad0bd7dd2aa84a312dc8e51083d14550ed35`.
- Prior source finding: `git show 583f8a9b:development/research/findings/1.8.9--1.9.4/neighbor-cell-collision-scan.md`, byte SHA-256 `e3187dc7f2e394d94cecab2819d20b51ad44f19a62bb9b7f727933634c1e2789` (4,154 bytes). This finding is context for the excluded scan/geometry topic only.
- Compared exact Ornithe Feather sources: 1.8.9, 1.9 and 1.9.4. The 1.8.9 and 1.9.4 World.java hashes match their source manifests. For 1.9, the World.java hash matches the embedded `.decompile-provenance.json` path hash.

## Audit checks

- In each release, the normal player class extends `LivingEntity`; its ordinary movement invokes inherited `Entity.move`, whose collision queries pass `this` and the current swept/adjusted player box to `World.getCollisions`. This establishes a non-null entity on the examined player path.
- For non-null entities, all three collision methods snapshot the outside-border flag and the result of `isWithinBorder` before the scan. The border test uses the entity’s outside flag to contract all four bounds by one block or expand them by one block, then applies the same strict center comparisons (`x > minX && x < maxX && z > minZ && z < maxZ`).
- Within eligible scanned cells, each version applies the same cached-snapshot transition: clear the flag if the initial outside and inside values are both true; set it if both are false. The cached inside value is not recomputed after those writes.
- Each version initializes the selected collision state to stone, then loads the world state only under `worldBorder.contains(position) || !initialInside`, and calls that selected state’s collision provider afterward. The fallback choice and ordering are equivalent for this non-null path.
- The 1.9 null guard changes behavior for null callers, but ordinary player movement supplies a non-null entity. The changing scan bounds/pruning and piston geometry belong to the independently reviewed WORLD-26 topic and were excluded. The server chunk-loading gate was also excluded.

## Relevant source digests

- 1.8.9 World.java: `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee`
- 1.9 World.java: `d1e23db2e20c86eb37b964a0df6838f1422ade7b0c2fff9c869ff8345979aa98`
- 1.9.4 World.java: `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a`

Disposition: accept the comparison’s bounded no-difference conclusion for WORLD-27. This verdict does not audit full-pair coverage, establish behavior outside the stated ordinary player path, or assess the separate neighbor scan/geometry claim.
