# Fence gate source provenance

Canonical source roots consumed read-only:

- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.1.2\unobfuscated`
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.2\unobfuscated`

| Identity | 26.1.2 | 26.2 |
|---|---|---|
| Exact version / mapping | `26.1.2` / `unobfuscated` | `26.2` / `unobfuscated` |
| Ready marker SHA-256 | `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7` | `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f` |
| Provenance sidecar SHA-256 | `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a` | `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1` |
| Source manifest SHA-256 (published identity; recomputed from bytes) | `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` |
| Ready-declared source file count | 6,882 | 7,055 |
| Ready-declared artifact manifest SHA-256 | `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` | `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba` |
| Ready-declared client SHA-256 | `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0` | Not declared in the 26.2 ready marker |

The actual ready markers identify the exact version and unobfuscated mapping, report `status=ready`, and bind the listed source manifest digests. Each relevant source file listed in `source-roster.md` was hashed from the canonical tree and its hash matched the corresponding source-manifest row. The source trees were not regenerated or modified. This is relevant-file verification, not a full source-tree rehash. No online mapping lookup was used.