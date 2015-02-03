package splitfindmin;

public class ElementCompressor {

	static ElementCompressorResult compressor(Element pos, int size, boolean direction) {
		ElementCompressorResult compressed = new ElementCompressorResult();
		compressed.compressed = new Element(Integer.MAX_VALUE);
		compressed.pos = pos;
		for (int i = 0; i < size; i++) {
			compressed.pos.parent = compressed.compressed;
			compressed.compressed.value = Math.min(compressed.compressed.value, compressed.pos.value);
			compressed.pos = direction ? compressed.pos.next : compressed.pos.prev;
		}
		return compressed;		
	}
	
	static class ElementCompressorResult {
		Element compressed;
		Element pos;

		private ElementCompressorResult() {
		}
	}
}
