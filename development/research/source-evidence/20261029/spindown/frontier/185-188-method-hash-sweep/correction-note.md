# Hash table header correction

The original artifacts remain unchanged. The original TSV header used literal backtick-plus-	 text where tab separators were intended; data rows already contained tabs. Use movement-core-sha256.corrected.tsv as the valid tab-separated supplement.

This supplement preserves all data rows exactly and replaces only the header separators with actual tab characters.

| Artifact | SHA-256 |
|---|---|
| Original movement-core-sha256.tsv | $origHash |
| Original eport.md | $reportHash |
| Supplement movement-core-sha256.corrected.tsv | $corrHash |
