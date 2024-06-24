import os
script_path = os.path.dirname(os.path.realpath(__file__))
files_path = os.path.join(script_path, '../../files/')

class OpenvinoInferenceEngineTest(object):
    ie_input_files = {'ie_python_sample': 'benchmark_app.py',
                      'model': 'horizontal-text-detection-0001.xml'}

    def __init__(self, target, work_dir):
        self.target = target
        self.work_dir = work_dir

    def setup(self):
        python_cmd = 'from openvino import Core ; print(Core().available_devices)'
        __, output = self.target.run('python3 -c "%s"' % python_cmd)
        self.available_devices = output

    def tear_down(self):
        self.target.run('rm -rf %s' % self.work_dir)

    def test_check_if_openvino_device_available(self, device):
        if device not in self.available_devices:
            return False, self.available_devices
        return True, self.available_devices

    def test_openvino_ie_benchmark_with_device(self, device, ir_files_dir):
        input_model = os.path.join(ir_files_dir, self.ie_input_files['model'])
        cmd = f'benchmark_app -hint throughput -m {input_model} -d {device}'
        return self.target.run(cmd)

    def test_openvino_ie_benchmark_python_api_with_device(self, device, ir_files_dir):
        python_sample = self.ie_input_files['ie_python_sample']
        input_model = os.path.join(ir_files_dir, self.ie_input_files['model'])
        cmd = f'{python_sample} -hint throughput -m {input_model} -d {device}'
        return self.target.run(cmd)
