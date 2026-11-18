# Encoding receipt and unchanged source verdict

This receipt adds LF-normalized JSON copies for handoff. The original files and original verdict remain unchanged. All digests below are SHA-256 over raw file bytes.

## Bound inputs and normalized copies

- Original source-roster.json: SHA-256 c5a39b8a201aed35b1540a03d284a89208b9b3cd484d59459dc1ad7253502439; 5,601 bytes; 161 CRLF pairs and one final bare LF.
- Normalized source-roster.lf.json: SHA-256 26568133fba307558387d5cfffb41d443ad7fb1c6641e4d020205898c6abab05; 5,440 bytes; 162 LF, zero CR, no BOM.
- Original audit.json: SHA-256 df068ab9f2b9a04e66ba99a9e51763cc51138a6acdf1ae5c4896a553b58d2775; 7,987 bytes; 306 CRLF pairs and one final bare LF.
- Normalized audit.lf.json: SHA-256 3f664d79041ae4235a03c51a135d49ccd3662da2c6787aff67101347417b8153; 7,681 bytes; 307 LF, zero CR, no BOM.
- Original verdict.md: SHA-256 c65dbfd53a0479859f68ec3d9fc4c603a535e89ee002173429a4efdbebf6c9ab; unchanged.

## Verification

Both original and normalized JSON parse successfully, and their decoded values compare equal. Replacing LF with CRLF in each normalized copy, then restoring the final newline to bare LF, reconstructs the exact original raw SHA-256 for each file. The normalized copies differ from their originals only by CRLF-to-LF conversion.

## Unchanged source acceptance

**DoorBlock: accepted** for the bounded stationary collision-shape claim in 26.1.2 → 26.2 unobfuscated sources. Closed FACING selection and open hinge-adjusted selection produce the same 3/16 directional edge panels; HALF and POWERED do not affect the provider.

**TrapDoorBlock: accepted** for the bounded stationary collision-shape claim in the same exact pair. Open FACING selection and closed TOP/DOWN versus BOTTOM/UP selection produce the same vertical panels and 3/16 horizontal slabs; POWERED and WATERLOGGED do not affect the provider.

No new source claim or scope is introduced by this encoding-only receipt. The verdict remains limited to the two stationary shape providers and their reviewed collision/cache/registration dependencies.
