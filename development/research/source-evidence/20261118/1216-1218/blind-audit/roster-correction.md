# Append-only roster correction

Applies to frozen roster SHA-256 `D3A172F7F169DA08F8979EA09411F651AD09590D20ABCC83E99DB5E31FCF4FD8`. The 1.21.6 raw client `client.jar` SHA-256 transcription in the frozen roster contained an explicit placeholder. The exact provenance value is `9592df8587d6d3ec0334c873ad432dc76006d6129afdce9a7eac52638339a24a` (URL object `740a125b83dd3447feaa3c5e891ead7fbb21ae28`). No source bodies had been read when this correction was added. The original roster remains unchanged; use the corrected value for subsequent pin validation.
