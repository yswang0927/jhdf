package io.jhdf;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BTreeNodeTest {
	private FileChannel fc;
	private RandomAccessFile raf;
	private Superblock sb;

	@BeforeEach
	public void setUp() throws FileNotFoundException {
		final String testFileUrl = this.getClass().getResource("test_file.hdf5").getFile();
		raf = new RandomAccessFile(new File(testFileUrl), "r");
		fc = raf.getChannel();
		sb = Superblock.readSuperblock(fc, 0);
	}

	@AfterEach
	public void after() throws IOException {
		raf.close();
		fc.close();
	}

	@Test
	public void testBTreeNode() throws IOException {
		BTreeNode bTree = new BTreeNode(fc, 136, sb);

		assertThat(bTree.getNodeType(), is(equalTo((short) 0)));
		assertThat(bTree.getNodeLevel(), is(equalTo((short) 0)));
		assertThat(bTree.getEntriesUsed(), is(equalTo((short) 1)));
		assertThat(bTree.getLeftSiblingAddress(), is(equalTo(Constants.UNDEFINED_ADDRESS)));
		assertThat(bTree.getRightSiblingAddress(), is(equalTo(Constants.UNDEFINED_ADDRESS)));
		assertThat(bTree.getKeys(), is(equalTo(new long[] { 0, 24 })));
		assertThat(bTree.getChildAddresses(), is(equalTo(new long[] { 1504 })));
		assertThat(bTree.toString(), is(equalTo(
				"BTreeNode [address=0x88, nodeType=GROUP, nodeLevel=0, entriesUsed=1, leftSiblingAddress=UNDEFINED, rightSiblingAddress=UNDEFINED]")));
	}

}