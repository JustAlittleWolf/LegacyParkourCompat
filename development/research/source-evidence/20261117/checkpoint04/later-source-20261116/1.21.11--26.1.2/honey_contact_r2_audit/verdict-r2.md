# Independent source audit — HoneyBlock report r2

**ACCEPT** for report SHA-256 `CC3704A76795E904C55BCF8D4FDDED2E9178A98A93A13841D5AA5E6D58517D93`.

## Correction verified

The r2 report corrects the 26.1.2 `net/minecraft/world/entity/player/Player.java` source hash to `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`. The local file at `decompiled_minecraft/26.1.2/unobfuscated/net/minecraft/world/entity/player/Player.java` hashes to that value (case-insensitive SHA-256 comparison), and the exact `unobfuscated.sources.sha256` entry matches it.

Comparison against the prior report, after substituting the corrected hash, found no substantive content changes; the r2 has only an additional terminal blank line. The r1 source audit `8CAE7DFD8DD53AD148ED648DF619F74BD2AE250F1A6BF3C176F9C03CF28B5FBC` verified the technical HoneyBlock movement claims and all other identity/source rows. Those unchanged checks are reused here; no source tree was rehashed or re-audited.

## Limits

This audit accepts the hash-only correction and reuses the prior bounded source verdict. It does not extend the HoneyBlock slice, establish pair-wide coverage, implementation applicability, or runtime parity.
