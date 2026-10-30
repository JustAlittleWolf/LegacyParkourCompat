# Independent review: 1.8.9 chest serialized-state and context projection

Verdict: the scoped projection is supported by the exact endpoint sources. The report correctly leaves modern-versus-legacy collision parity open for arrangements outside the serialized tuple and legacy neighbor context it documents.

Reviewed candidate artifacts:

- `.native-source/20261030/chest/FINDING.md`, SHA-256 `30ADD2A05DF5CC82D10030AE2325B9ACA113059380E23FCED1779F48A36969FF`.
- `.native-source/20261030/chest/metadata-comparison.tsv`, SHA-256 `47A81F94E7FAD7480180BE4A7078DF9F63B47CF340B38AA37DEBB78C538D024C`.
- `.native-source/20261030/chest/source-hashes.sha256`, SHA-256 `01A00EAD29D2A425B5822B46160434EAFFD80F633D8F004E6DAFC37B9A871791`.

Provenance is supported by canonical manifests for 1.8.9 `ornithe-feather` (`feather-gen2-1.8.9+build.2`) and 26.2 `unobfuscated`. Every canonical Java hash in the candidate's source manifest matched on recomputation. The reused 2028 projection, ordered rename list, and accepted crosswalk artifacts also matched the exact hashes recorded by the candidate.

The table contains all 32 tuples (IDs 54 and 146, metadata 0–15). I independently recomputed the old orientation from `Direction.byId(metadata)` for all values and confirmed every match/mismatch label against the flattened `facing` property. The metadata-zero and default-fill routes, explicit rows 866–869 and 2338–2341, names, `type=single`, and ordered rename traces agree with `BlockStateData` and the section conversion source. The two old IDs instantiate distinct chest objects; the legacy shape updater checks same-instance neighbors in north, south, west, east order, independently of facing, and mutates the queried singleton bounds. The 26.2 registry has distinct chest/trapped-chest blocks with defaults north/single/false; the property codec resolves absent properties to the registered default. Current pair selection and collision halves use facing-relative `TYPE` and are not equivalent to the legacy precedence scan. The report correctly instructs preserving vanilla states.

No unresolved source or tuple gate remains within this projection. The existing shape-compatibility gate remains open: exact tuples and current facing/context do not prove historical box parity for every current arrangement. No runtime movement claim is accepted.
