from oeqa.runtime.case import OERuntimeTestCase
from oeqa.runtime.decorator.package import OEHasPackage
from oeqa.core.decorator.depends import OETestDepends
from oeqa.runtime.ovutils.targets.oeqatarget import OEQATarget
from oeqa.runtime.ovutils.tests.horizontal_model_download_test import HorizontalModelDownloadTest
from oeqa.runtime.ovutils.tests.openvino_inference_engine_test import OpenvinoInferenceEngineTest
from oeqa.runtime.ovutils.openvinoutils import get_testdata_config

class OpenvinoInferenceEngine(OERuntimeTestCase):

    @classmethod
    def setUpClass(cls):
        cls.hori_download = HorizontalModelDownloadTest(OEQATarget(cls.tc.target), '/tmp/ie/ir')
        cls.hori_download.setup()
        cls.openvino_ie = OpenvinoInferenceEngineTest(OEQATarget(cls.tc.target), '/tmp/ie/inputs')
        cls.openvino_ie.setup()
        cls.ir_files_dir = '/tmp/ie/ir'

    @classmethod
    def tearDownClass(cls):
        cls.openvino_ie.tear_down()
        cls.hori_download.tear_down()

    @OEHasPackage(['wget'])
    def test_openvino_ie_can_download_ir(self):
        proxy_port = get_testdata_config(self.tc.td, 'OPENVINO_PROXY')
        if not proxy_port:
            self.skipTest('Need to configure bitbake configuration (OPENVINO_PROXY="proxy.server:port").')
        (status, output) = self.hori_download.test_can_download_horizontal_text_model_topology(proxy_port)
        self.assertEqual(status, 0, msg='status and output: %s and %s' % (status, output))

        (status, output) = self.hori_download.test_can_download_horizontal_text_model_weights(proxy_port)
        self.assertEqual(status, 0, msg='status and output: %s and %s' % (status, output))

    @OETestDepends(['openvino_inference_engine_test_wrapper.OpenvinoInferenceEngine.test_openvino_ie_can_download_ir'])
    @OEHasPackage(['openvino-inference-engine'])
    @OEHasPackage(['openvino-inference-engine-samples'])
    def test_openvino_ie_benchmark_with_cpu(self):
        (status, output) = self.openvino_ie.test_openvino_ie_benchmark_with_device('CPU', self.ir_files_dir)
        self.assertEqual(status, 0, msg='status and output: %s and %s' % (status, output))

    @OETestDepends(['openvino_inference_engine_test_wrapper.OpenvinoInferenceEngine.test_openvino_ie_can_download_ir'])
    @OEHasPackage(['openvino-inference-engine'])
    @OEHasPackage(['openvino-inference-engine-samples'])
    @OEHasPackage(['intel-compute-runtime'])
    def test_openvino_ie_benchmark_with_gpu(self):
        (status, output) = self.openvino_ie.test_openvino_ie_benchmark_with_device('GPU', self.ir_files_dir)
        self.assertEqual(status, 0, msg='status and output: %s and %s' % (status, output))

    @OETestDepends(['openvino_inference_engine_test_wrapper.OpenvinoInferenceEngine.test_openvino_ie_can_download_ir'])
    @OEHasPackage(['openvino-inference-engine'])
    @OEHasPackage(['openvino-inference-engine-python3'])
    @OEHasPackage(['python3-opencv'])
    @OEHasPackage(['python3-numpy'])
    def test_openvino_ie_benchmark_python_api_with_cpu(self):
        (status, output) = self.openvino_ie.test_openvino_ie_benchmark_python_api_with_device('CPU', self.ir_files_dir)
        self.assertEqual(status, 0, msg='status and output: %s and %s' % (status, output))

    @OETestDepends(['openvino_inference_engine_test_wrapper.OpenvinoInferenceEngine.test_openvino_ie_can_download_ir'])
    @OEHasPackage(['openvino-inference-engine'])
    @OEHasPackage(['openvino-inference-engine-python3'])
    @OEHasPackage(['intel-compute-runtime'])
    @OEHasPackage(['python3-opencv'])
    @OEHasPackage(['python3-numpy'])
    def test_openvino_ie_benchmark_python_api_with_gpu(self):
        (status, output) = self.openvino_ie.test_openvino_ie_benchmark_python_api_with_device('GPU', self.ir_files_dir)
        self.assertEqual(status, 0, msg='status and output: %s and %s' % (status, output))

