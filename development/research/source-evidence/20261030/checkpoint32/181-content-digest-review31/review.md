# 1.8.1 mapped archive content-digest review

- Candidate: `fix/181-enum-preparation-20261030`, commit `7a36da839ec3918c91c0b0b8f86d0800598652e3` (`e629ff20..HEAD`).
- Decision: **ACCEPT** for the requested deterministic content-digest correction.
- Scope: static review only; no source edits, build, tests, decompilation, game launch, or downloads.

## Independent digest recomputation

Used Python standard-library `zipfile`, `hashlib`, and `struct` against the primary checkout's cached artifacts. Recomputed `SHA-256("LPC-JAR-CONTENT-v1\\0" || entries...)`, with entries sorted by decoded path string and each entry encoded as big-endian 32-bit UTF-8 path length, UTF-8 path bytes, big-endian 64-bit uncompressed content length, and uncompressed bytes.

| Archive | Whole-file SHA-256 | Entries | Duplicate paths | Content SHA-256 |
|---|---|---:|---:|---|
| `client-ornithe-feather.jar` | `d2977e05623d692e2d73dacbd0d6328b9226f47d80f824711a6d5fe81df09001` | 2,673 | 0 | `c7527d0279c8a24e70b5b4103c0606dc883ff840b5d5afb827edbe9bda4abaf7` |
| `client-ornithe-feather-181-enum-helper-baseline-v1.jar` | `efdea48f34168863a281ea3da34fe237076e05e4a6dba2efb29b15004da46ffe` | 2,673 | 0 | `c7527d0279c8a24e70b5b4103c0606dc883ff840b5d5afb827edbe9bda4abaf7` |

Both archives have 173 directory entries and all paths are ASCII, so Java `TreeMap<String,...>` ordering and the independent Python ordering agree for these pinned inputs. The candidate's length framing, fixed big-endian encoding, domain/version prefix, and duplicate decoded-path rejection make the digest unambiguous for this pinned archive. The recomputed digest exactly matches both `MAPPED_CLIENT_CONTENT_SHA256` and the expected recipe hash.

## Code and documentation review

- `verifyOrdinaryRemap` now checks the canonical content digest, so differing ZIP timestamps/compression metadata do not invalidate identical remaps.
- On a clean source output or `--force`, the remap task runs before `verifyOrdinaryRemap`; `remap` deletes any stale destination before writing. Existing source reuse returns before remapping, with the pipeline ID changed to record the new gate. The documented force workflow remains consistent.
- Raw client, Tiny mapping, Feather artifact SHA-256 constants/checks, the 41-helper bindings, 59 diagnostic bindings, full post-repair archive delta checks, and strict Vineflower ERROR gate are unchanged by this delta. The diff changes only the mapped archive pin/recipe identifier, content hashing, and the matching README explanation.
- README wording accurately describes sorted paths, UTF-8 path and uncompressed content hashing, explicit length framing, duplicate rejection, retained exact input gates, and the separate post-repair archive comparison.

## Blockers

None found within the assigned scope.
