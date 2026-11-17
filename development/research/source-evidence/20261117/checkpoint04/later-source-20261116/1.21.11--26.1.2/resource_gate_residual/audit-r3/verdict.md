# Independent citation audit — resource packet r2 erratum

## Verdict

**ACCEPT** the citation-only correction. The input-reference r2 changes only the two `officialArtifactMetadata[].metadataSha1` fields; both values match the exact metadata object SHA-1s recorded in the original independent resource verdict. Every other parsed field is unchanged from r1. The r3 erratum binds the exact r1, r2, and prior r2-audit file hashes, contains no placeholders or control characters, and identifies the corrected values accurately.

## Immutable bindings

- Original input reference: `input-reference.json`, SHA-256 `9D9A91EEBD8481972D5626C8C979E0A8DF3841F056FE45EE7F902455BFAF4447`.
- Corrected input reference r2: `input-reference-r2.json`, SHA-256 `81681EC9D8DE1D1B033C5FB73BF12206C0126E9633DC561A1FC5DB1D7FE8A281`.
- Correction erratum r3: `resource-membership-r3-erratum.md`, SHA-256 `3C312390B4483A451085AE08961E212D1BAAA352671E6F7E5D228742760D1ED8`.
- Prior citation audit: `audit-r2/verdict.md`, SHA-256 `519AD340CB4A2CB1E5B7DAF55F21D8B53732D77E879F435472E5EF2076BD3C4A`.
- Original full resource audit: `audit/verdict.md`, SHA-256 `7DBD66218F81B8FB1043D9F1190CC1A103FD9E8918CF397D56553F56A9D41575`.

Corrected values copied in r2 match the prior independent resource audit: 1.21.11 `718cd84b4912691fe5de829aab6876a4b7f6c5c4`; 26.1.2 `8cca2c0efc47ef22351e5073bb1f5e7ab339279b`. The r3 erratum contains the exact r1/r2/audit-r2 digests and no placeholder or non-whitespace control byte.

The previous independent source, official metadata, pinned-JAR and ten-entry archive-membership checks are reused without re-review. This acceptance changes no source or movement claim and establishes no effective override, live-world reachability, runtime parity, or pair freeze.
