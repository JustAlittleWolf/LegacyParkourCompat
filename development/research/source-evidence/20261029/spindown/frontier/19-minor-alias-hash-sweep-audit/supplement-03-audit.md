# Independent audit: Supplement 03

## Review identity

Reviewed `.native-source/20261028/frontier-c/19-minor-alias-hash-sweep/supplement-03.md` at SHA-256 `e8f51bf73cba6cbf25c7eb02498865a7ad1a8c2b813956590df07201e84bbd85`. Its bound artifact identities match the current files: `supplement.md` SHA-256 `6a72afe0af8fcab041ddcbe7c96598cd4a4657334181fe3a346a3e4aa690eb30`; `supplement-02.md` SHA-256 `421fad6d7c0d9a210954c0e060da4ab2038f40895c335940a5db761931c78e63`.

## Hash and source checks

Both corrected 1.9.3 hashes match the canonical `ornithe-feather` source files and their `.decompile-provenance.json` `sources` entries:

- `net/minecraft/entity/ai/pathing/WalkNodeEvaluator.java`: `893108191844cee1eceb32fbe9c245a70cad969c02c8895b17e0475436b4c85c`.
- `net/minecraft/block/FireBlock.java`: `48b734bf3665b9c743c05c176bb1208992e357f376fefcb1b6d337dade5a2c29`.

These correct the two transcription errors identified in my Supplement 02 audit. They do not change the underlying source classifications.

## Collision scan gate check

The 1.9.3 `World.getCollisions(Box)` body at lines 1018–1052 exactly supports the correction. It computes `i=floor(minX)-1`, `j=ceil(maxX)+1`, `m=floor(minZ)-1`, `n=ceil(maxZ)+1`, and iterates `o=i..<j`, `p=m..<n`, so the upper scanned integer is `ceil(max)` and the lower includes one cell before `floor(min)`. It computes `q` as the sum of x-edge and z-edge indicators, skips columns with `q==2`, and probes `isChunkLoaded` at `(o,64,p)`. For admitted columns, it scans `r=k..<l`, with `k=floor(minY)-1`, `l=ceil(maxY)+1`; when `q>0`, it excludes vertical endpoints `r==k` and `r==l-1`, while `q<=0` admits all scanned y positions. Only after those gates and setting `(o,r,p)` does it return true for `o<-30,000,000`, `o>=30,000,000`, `p<-30,000,000`, or `p>=30,000,000`.

Thus Supplement 03's padding and `q`, y-endpoint, and loaded-chunk qualifications agree with the canonical source and the prior audit. The candidate box need not itself extend past the horizontal limit; a padded, loaded, non-skipped scan cell can reach the bound check. This confirms source control flow only and does not establish that the precondition is reachable in ordinary client play.

## Status

This bounded addendum verifies the hash corrections and scan-boundary wording. Earlier reviews and audit files remain unchanged. No pair freeze, movement finding, or parity conclusion is made.
